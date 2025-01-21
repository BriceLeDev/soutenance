package com.soutenence.publiciteApp.config;


import com.soutenence.publiciteApp.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

//<Integer> designe le type du owner primary key
public class ApplicationAuditeAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer>getCurrentAuditor() {

        //recupération de l instance de l utilisateur connecter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
             authentication instanceof AnonymousAuthenticationToken

        )
        {
            return Optional.empty();

        }
        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.of((int) userPrincipal.getId());
    }
}

