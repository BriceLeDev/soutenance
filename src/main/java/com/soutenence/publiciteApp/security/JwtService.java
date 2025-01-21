package com.soutenence.publiciteApp.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    //@Value("${application.security.jwt.expiration}")
    long jwtExpiration= 1000*60*60*24; //24H min
    long RefreshmentJwtExpiration = 604800000; //24h
    //@Value("${application.security.jwt.secret-key}")
    private static String secretKey = "a716bb889fe1b847993d6beca40941e2585d45fb5fedfd86afb026364ffc1ab051159eacfecda25a820b8bacc33f9a5ea97b5e4cfcf1a6f288270e8e2e53fbee" ;
    public String generateToken(UserDetails userDetails){
        return   generateToken(new HashMap<>(), userDetails);
    }

    //les claims permettent d'ajouter d'autres informations au token
    public String generateToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims,userDetails,jwtExpiration);
    }

    public String generateRefreshJwt(
            UserDetails userDetails
    ){
        return buildToken(new HashMap<>(),userDetails,RefreshmentJwtExpiration);
    }

    //methode qui fabrique le token
    private String buildToken(HashMap<String,
            Object> extraClaims,
            UserDetails userDetails,
            long jwtExpiration){
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();//recupérer la liste des autorisations de l'utilisateur
        return Jwts
                .builder()
                .claims(extraClaims)// Ajout des claims supplémentaires (si présents) dans le JWT
                .subject(userDetails.getUsername())// Définition du sujet du JWT comme étant le nom d'utilisateur
                .issuedAt(new Date(System.currentTimeMillis()))//Définition du moment de création du JWT
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) //Définition de la date d'expiration du JWT
                .claim("authorities", authorities) //Ajout des autorisations (rôles) comme un claim dans le JWT
                .signWith(getSignKey()) // Signature du JWT avec une clé secrète (getSignKey() doit retourner une clé de signature valide)
                .compact(); // Compactage et sérialisation du JWT en une chaîne de caractères
    }
    /*private String buildRefreshToken(
            HashMap<String,Object> extraClaims,
            UserDetails userDetails,
            long RefreshmentJwtExpiration) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();//recupérer la liste des autorisations de l'utilisateur
        return Jwts
                .builder()
                .claims(extraClaims)// Ajout des claims supplémentaires (si présents) dans le JWT
                .subject(userDetails.getUsername())// Définition du sujet du JWT comme étant le nom d'utilisateur
                .issuedAt(new Date(System.currentTimeMillis()))//Définition du moment de création du JWT
                .expiration(new Date(System.currentTimeMillis() + RefreshmentJwtExpiration)) //Définition de la date d'expiration du JWT
                .claim("authorities", authorities) //Ajout des autorisations (rôles) comme un claim dans le JWT
                .signWith(getSignKey()) // Signature du JWT avec une clé secrète (getSignKey() doit retourner une clé de signature valide)
                .compact(); // Compactage et sérialisation du JWT en une chaîne de caractères
    }*/
    //methode qui fabrique la clé de signature du token

    private static SecretKey getSignKey() {
        //byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //vérifie si le token est valide
    public boolean isValidToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpire(token);
    }

    //vérifie si le token est expirer
    public boolean isTokenExpire(String token) {
        return extractExpiration(token).before(new Date());
    }

    //extraire la date d'expiration du token
    private Date extractExpiration(String token) {
        Date dt = extracClaim(token,Claims::getExpiration);
    return dt;
    }



    //extraire le username du token
    public static String extractUsername(String token) {
        return extracClaim(token, Claims::getSubject);
    }

    private static <T> T extracClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extraAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static Claims extraAllClaims(String token) {

        return  (Claims) Jwts.parser()
                .verifyWith(getSignKey()) // <----
                .build()
                .parseSignedClaims(token)
                .getPayload()
                ;
            /*    Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();*/
    }



}
