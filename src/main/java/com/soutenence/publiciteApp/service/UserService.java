package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.UserMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.UserResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.adminFormRequest;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.repository.UserRepository;
import com.soutenence.publiciteApp.validationObjet.RegistrationFormRequest;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperClass userMapperClass;
    private final AuthenticationService authenticationService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapperClass userMapperClass, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapperClass = userMapperClass;
        this.authenticationService = authenticationService;
    }


    public UserResponse getUser(long userId) {
        return this.userRepository.findById(userId)
                .map(userMapperClass::toUserResponse)
                .orElseThrow(()->new EntityNotFoundException("Ce utilisateur n'existe pas!"));
    }
    public UserResponse getUserByEmail(String email) {
        log.info("Mon email {}",email);
        String mail = email;
        log.info("Recherche utilisateur avec email: {}", email);
        Optional<User> user1  = userRepository.findByEmailIgnoreCase(email);
        log.info("essai test user {}", user1);
        return this.userRepository.findByEmailIgnoreCase(email)
                .map(user -> {
                    log.info("Utilisateur trouvé: {}", user);
                    return userMapperClass.toUserResponse(user);
                })
                .orElseThrow(() -> {
                    log.error("Utilisateur non trouvé pour l'email: {}", email);
                    return new EntityNotFoundException("Cet utilisateur n'existe pas!");
                });
    }


    public UserResponse updateUser(long userId, RegistrationFormRequest request) {

        User user = this.userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("Utilisateur non trouvé"));
        user.setEmail(request.getEmail());
        user.setNonUtilisateur(request.getNonUtilisateur());
        user.setPassword(request.getPassword());

        this.userRepository.save(user);
        return this.userMapperClass.toUserResponse(user);
    }

    public void addAdmin(adminFormRequest request) throws MessagingException {
        var user = User.builder()
                .email(request.getEmail())
                .nonUtilisateur(request.getNonUtilisateur())
                .numero(request.getNumero())
                .fidelisation(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .roles(request.getUserRole())
                .build();
        userRepository.save(user);
        this.authenticationService.sendValidationEmail(user);
    }

    public PageResponse<UserResponse> getAllUsersCustomer(int page, int size) {
        String role="USER";
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllUserByRole(pageable,role);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );
    }
    public PageResponse<UserResponse> getUsersCustomerByMail(String mail,int page, int size) {
        String role="USER";
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllUserByRoleAndMail(pageable,role,mail);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );
    }
    public PageResponse<UserResponse> getAllUsersCustomerBetween2Date(LocalDate date1, LocalDate date2, int page, int size) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllCustomerByCreatedATBetween(pageable,date1,date2);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );
    }

    public PageResponse<UserResponse> getAllUsersAdmin(int page, int size) {
        String role="ADMIN";
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllUserByRole(pageable,role);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()

        );
    }
    public PageResponse<UserResponse> getUsersAdminByMail(String mail, int page, int size) {
        String role="ADMIN";
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllUserByRoleAndMail(pageable,role,mail);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()

        );
    }

    public PageResponse<UserResponse> getAllUsersCustomerAbnment(int page, int size) {


        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllUserByAbnmentActif(pageable);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );
    }

    public PageResponse<UserResponse> getAllUsersCustomerNoAbnment(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAT"));
        Page<User> customerPage = userRepository.findAllUserByNoActifAbnment(pageable);
        List<UserResponse> customerList = customerPage.stream()
                .map(userMapperClass::toUserResponse)
                .toList();
        return new PageResponse<>(
                customerList,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );
    }
    public ResponseEntity<?> setFidelisation(Long userId){
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("L'utilisateur n'existe pas"));
        if (user.isFidelisation()){
            throw new RuntimeException("Ce client est déjà fidélisé");
        }
        user.setFidelisation(true);
        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<?> setDeFidelisation(Long userId){
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("L'utilisateur n'existe pas"));
        if (user.isFidelisation()){
            throw new RuntimeException("Ce client est déjà défidélisé");
        }
        user.setFidelisation(false);
        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> blockUser(Long userId){
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("L'utilisateur n'existe pas"));
        if (user.isAccountLocked()){
            throw new RuntimeException("Ce client est déjà bloqué");
        }
        user.setAccountLocked(true);
        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deBlockUser(Long userId){
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("L'utilisateur n'existe pas"));
        if (user.isAccountLocked()){
            throw new RuntimeException("Ce client est déjà débloqué");
        }
        user.setAccountLocked(false);
        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
   /* public ResponseEntity<?> Fidelisation(Long userId){
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("L'utilisateur n'existe pas"));
        if (user.isFidelisation()){
            throw new RuntimeException("Ce client est déjà fidélisé");
        }
        user.setFidelisation(true);
        return ResponseEntity.ok().build();
    }*/

}
