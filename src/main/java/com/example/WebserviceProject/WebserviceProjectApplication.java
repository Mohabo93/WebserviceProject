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


	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1, "mohamad", passwordEncode.encode("123456"), roles);

			userRepository.save(admin);
		};
	}
}
