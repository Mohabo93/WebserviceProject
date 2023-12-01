package com.example.WebserviceProject;

import com.example.WebserviceProject.Entity.Role;
import com.example.WebserviceProject.Entity.User;
import com.example.WebserviceProject.Repository.RoleRepository;
import com.example.WebserviceProject.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class WebserviceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceProjectApplication.class, args);
	}


	// CommandLineRunner för att köra kod vid uppstart av programmet
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{

			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;	// Kontrollera om ADMIN-rollen redan finns

			// Skapa och spara ADMIN- och USER-rollerna
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			//Skapa en uppsättning roller med endast ADMIN-rollen
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			// Skapa en Admin-användare med ID 1, användarnamn & lösenord och tilldelade roller
			User admin = new User(1, "mohamad", passwordEncode.encode("123456"), roles);

			// Sparar Admin-användaren i userRepository
			userRepository.save(admin);
		};
	}
}
