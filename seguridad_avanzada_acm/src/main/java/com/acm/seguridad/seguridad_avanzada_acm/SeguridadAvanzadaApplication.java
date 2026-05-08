package com.acm.seguridad.seguridad_avanzada_acm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.acm.seguridad.seguridad_avanzada_acm.models.Role;
import com.acm.seguridad.seguridad_avanzada_acm.models.User;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.RoleRepository;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class SeguridadAvanzadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeguridadAvanzadaApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
            if (!userRepository.existsByUsername("admin")) {
                Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
                Role userRole = roleRepository.findByName("USER").orElse(null);

                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@acm.com");
                adminUser.setPassword(passwordEncoder.encode("password123"));
                adminUser.setDepartment("IT");
                if (adminRole != null && userRole != null) {
                    adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
                } else if (adminRole != null) {
                    adminUser.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
                }
                userRepository.save(adminUser);
            }

            if (!userRepository.existsByUsername("usuario")) {
                Role userRole = roleRepository.findByName("USER").orElse(null);
                User normalUser = new User();
                normalUser.setUsername("usuario");
                normalUser.setEmail("usuario@acm.com");
                normalUser.setPassword(passwordEncoder.encode("password123"));
                normalUser.setDepartment("SALES");
                if (userRole != null) {
                    normalUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));
                }
                userRepository.save(normalUser);
            }
		};
	}
}
