package com.soutenence.publiciteApp;

import com.soutenence.publiciteApp.entity.Role;
import com.soutenence.publiciteApp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
public class PubliciteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubliciteAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository  roleRepository){
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}
		};
	}
}
