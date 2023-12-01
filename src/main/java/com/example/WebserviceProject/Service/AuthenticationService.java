package com.example.WebserviceProject.Service;

import com.example.WebserviceProject.Entity.LoginResponseDTO;
import com.example.WebserviceProject.Entity.Role;
import com.example.WebserviceProject.Entity.User;
import com.example.WebserviceProject.Repository.RoleRepository;
import com.example.WebserviceProject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository; //Injicering av UserRepository för att utföra operationer mot användaruppgifter

    @Autowired
    private RoleRepository roleRepository; //Injicering av RoleRepository för att hantera användarroller

    @Autowired
    private PasswordEncoder passwordEncoder; //Injicering av PasswordEncoder för att koda och dekoda lösenord

    @Autowired
    private AuthenticationManager authenticationManager; //Injicering av AuthenticationManager för att hantera autentisering

    @Autowired
    private TokenService tokenService; //Injicering av TokenService för att generera JWT-tokens

    // Registrerar en ny användare
    public User registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow();

        Set<Role>  authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepository.save(new User(0, username, encodedPassword, authorities));
    }

    // Loggar in en användare och genererar en JWT-token
    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }
}
