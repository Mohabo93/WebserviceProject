package com.example.WebserviceProject.Controller;

import com.example.WebserviceProject.Entity.LoginResponseDTO;
import com.example.WebserviceProject.Entity.RegistrationDTO;
import com.example.WebserviceProject.Entity.User;
import com.example.WebserviceProject.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    // Automatiskt injicera en instans av AuthenticationService
    @Autowired
    private AuthenticationService authenticationService;

    // Hanterar POST-förfrågningar för att registrera en ny användare
    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    // Hanterar POST-förfrågningar för att logga in en ny användare
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
