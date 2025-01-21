package com.soutenence.publiciteApp.security;

import com.soutenence.publiciteApp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    //@Transactional
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(useremail)
                .orElseThrow(()->new UsernameNotFoundException("Utlilisateur non trouvé"));
    }
}
