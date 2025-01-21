package com.soutenence.publiciteApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.Arrays;

@Configuration

public class BeanConfig {

    private final UserDetailsService userDetailService;

    public BeanConfig(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    /*public BeanConfig(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }*/

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       //bean qui gère l authentification de l utilisateur
        return config.getAuthenticationManager();
    }

    //Bean qui permet d alimenter les colonne CreatedBy et UpdateBy de toute les tables(auditingAward)
    @Bean
    public AuditorAware<Integer> auditorAware(){
        return new ApplicationAuditeAware();
    }

    // org.springframework.http.HttpHeaders;
    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        //corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://checkout.cinetpay.com"));
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200/", "https://checkout.cinetpay.com"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                org.springframework.http.HttpHeaders.ORIGIN,
                org.springframework.http.HttpHeaders.CONTENT_TYPE,
                org.springframework.http.HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
        ));
        corsConfiguration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "OPTIONS"
        ));
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
}

