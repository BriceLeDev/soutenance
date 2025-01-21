package com.soutenence.publiciteApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soutenence.publiciteApp.entity.Token;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.enums.EmailTemplateName;
import com.soutenence.publiciteApp.exceptionHandler.AccountAlreadyActiveException;
import com.soutenence.publiciteApp.exceptionHandler.NoActiveAccountException;
import com.soutenence.publiciteApp.exceptionHandler.TokenNonValideException;
import com.soutenence.publiciteApp.exceptionHandler.UserAlreadyExists;
import com.soutenence.publiciteApp.repository.RoleRepository;
import com.soutenence.publiciteApp.repository.TokenRepository;
import com.soutenence.publiciteApp.repository.UserRepository;
import com.soutenence.publiciteApp.security.JwtService;
import com.soutenence.publiciteApp.validationObjet.LoginFormRequest;
import com.soutenence.publiciteApp.validationObjet.LoginFormResponse;
import com.soutenence.publiciteApp.validationObjet.RegistrationFormRequest;

import jakarta.mail.MessagingException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    //il esimportant de ne pas oublier de créer un bean de type authManager dans beanconfig
    //authManager permet de vérifier si les donnée envoyées par l utilisateur s il
    //correspond exactement à ceux de la Bdd lors de la connection
    private final AuthenticationManager authenticationManager;

  //  @Value("${application.mailing.frontend.activation-url}")
    private  String activationUrl;

    public AuthenticationService(@Value("${application.mailing.frontend.activation-url}") String activationUrl, EmailService emailService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, TokenRepository tokenRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.emailService = emailService;
        this.activationUrl = activationUrl;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegistrationFormRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER").orElseThrow(()-> new IllegalStateException("Role non initialiser"));
        if(!Objects.equals(request.getPassword(), request.getConfirmPassword())){
            throw new RuntimeException("Les mots de passe sont incorrect");
        }
        Optional<User> user1 = this.userRepository.findByEmailIgnoreCase(request.getEmail());
        if(user1.isPresent()){
            throw new UserAlreadyExists("Compte existant veuillez vous connectez");
        }
        var user = User.builder()
                .email(request.getEmail())
                .nonUtilisateur(request.getNonUtilisateur())
                .password(passwordEncoder.encode(request.getPassword()))
                .numero(request.getNumero())
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);

    }

    public void sendValidationEmail(User user) throws MessagingException {
        
        //avant d envoyer le code de validation par mail on génère et enrégistre le token pour cet utilisateur
         var newToken = generateAndSaveActivateToken(user);

        //Envoie du mail
        emailService.sendMail(
            user.getEmail(),
            user.getNonUtilisateur(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl,
            newToken,
            "Activation de votre compte"
            
        );
    }

    //a chaque fois qu on génère un token on l enrégistre dans la base de donné
    private String
    generateAndSaveActivateToken(User user) {
        // Génération du token
        String tokenGenerated = generateActivationCode(6);
        var token = Token.builder()
                .token(tokenGenerated)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

         tokenRepository.save(token);
         return tokenGenerated;
    }

    //methode qui génère le code et qui prend le nombre de chiffre en paramètre
    private String generateActivationCode(int length) {
        String character = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i<length;i++){
            int randomIndex = secureRandom.nextInt(character.length()); //0....9;
            codeBuilder.append(character.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    //connexion du clien
    public LoginFormResponse login(LoginFormRequest request) {

        User userAuth = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(()-> new EntityNotFoundException("Utilisateur n existe pas"));
        if (!userAuth.isEnabled()){
                throw new NoActiveAccountException("Compte non activé impossible de se connecter");

        }
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var claim = new HashMap<String, Object>();
        var user = (User)auth.getPrincipal();
        claim.put("NomUtilisateur",user.getNonUtilisateur());
        String token = jwtService.generateToken(claim, user);
        String refreshToken = jwtService.generateRefreshJwt(user);
        return LoginFormResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }


    public void activateAccount(String token) throws MessagingException {
        Token AutToken = tokenRepository.findByToken(token)
                .orElseThrow(()->
                        new TokenNonValideException("Code non trouvé. Veuillez saisir un code valide"));
       if (AutToken.getUser().isEnabled()){
           throw new AccountAlreadyActiveException ("Ce compte est déjà activé.Veuillez vous connectez");
       }
        if(AutToken.getExpiredAt().isBefore(LocalDateTime.now())){
            sendValidationEmail(AutToken.getUser());
            throw new TokenNonValideException("Code expiré, un nouveau à été envoyer dans votre boite mail");

        }
        var user = userRepository.findById(AutToken.getUser().getId())
                .orElseThrow(()->new UsernameNotFoundException("Utilisateur non trouver"));
        user.setEnabled(true);
        userRepository.save(user);
        AutToken.setValidateAt(LocalDateTime.now());
        tokenRepository.save(AutToken);
    }

    public void refreshingToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //HttpHeaders.AUTHORIZATION springframework
        String authHeader = request.getHeader(AUTHORIZATION);
        String refreshToken;
        String userEmail;
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            return ;
        }
        refreshToken = authHeader.substring(7);
        userEmail = JwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            UserDetails user = userRepository.findByEmailIgnoreCase(userEmail)
                    .orElseThrow(()-> new EntityNotFoundException("Utilisateur n existe pas"));

            if (jwtService.isValidToken(refreshToken,user)) {
                String token = jwtService.generateToken(user);
                var authResponse = LoginFormResponse.builder()
                        .token(token)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }


    }
}
