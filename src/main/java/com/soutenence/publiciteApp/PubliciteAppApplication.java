package com.soutenence.publiciteApp;

import com.soutenence.publiciteApp.entity.Role;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.repository.RoleRepository;
import com.soutenence.publiciteApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
public class PubliciteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubliciteAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository  roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}
			if (roleRepository.findByName("ADMIN").isEmpty()){
				roleRepository.save(
						Role.builder().name("ADMIN").build()
				);
			}


			if (userRepository.findByEmailIgnoreCase("admin2@gmail.com").isEmpty()){
				Role role = roleRepository.findByName("ADMIN").orElseThrow(
						()-> new EntityNotFoundException("Role introuvable")
				);
				userRepository.save(
						User.builder()
								.email("admin2@gmail.com")
								.enabled(true)
								.nonUtilisateur("ABALO Jean")
								.password(passwordEncoder.encode("\"Admin@12345\""))
								.numero("92370843")
								.roles(List.of(role))
								.fidelisation(true)
								.createdAT(LocalDateTime.now())
								.build()
				);
			}
		};
	}
}
