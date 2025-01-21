package com.soutenence.publiciteApp.config;
import com.soutenence.publiciteApp.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TokenUtilitiInjector {

    @Bean
    public JwtService jwtService(){
        return new JwtService();
    }
}
