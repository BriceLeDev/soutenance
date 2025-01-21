package com.soutenence.publiciteApp.security;

import com.soutenence.publiciteApp.exceptionHandler.TokenExpiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Slf4j
@Service
//OncePerRequestFilter : transforme la classe en un filter pour filtrer les rquettes
public class JwtFilter extends OncePerRequestFilter  {
    private final   JwtService jwtService;
    private   final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(
           @NotNull HttpServletRequest request,
           @NotNull HttpServletResponse response,
           @NotNull FilterChain filterChain) throws ServletException, IOException {

        //log.info("le path de la requete est : " + request.getServletPath());
        if (request.getServletPath().contains("/auth"))
        {
            filterChain.doFilter(request,response);
            return;
        }
       // log.info("Processing request for URL: {}", request.getRequestURI());
       // log.info("Processing entete de la requete : {}", request.getHeader("Bearer"));
    
        String authHeader = request.getHeader(AUTHORIZATION);
        String jwt;
        String userEmail;
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        if (jwtService.isTokenExpire(jwt)){
            throw new TokenExpiredException("Session expirer Veuillez connectez à nouveau");
        }
        userEmail = JwtService.extractUsername(jwt);

// si l utilisateur n est pas authentifier
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

//On authentifie l utilisateur qui n était pas authentifier
            if (jwtService.isValidToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken autToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                autToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(autToken);
            }
        }
        filterChain.doFilter(request,response);

    }
}
