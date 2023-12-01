package com.example.WebserviceProject.Entity;

public class RegistrationDTO {

    // Användarnamn och Lösenord för registrering
    private String username;
    private String password;

    //StandardConstructor
    public RegistrationDTO() {
        super();
    }

    // Constructor med Användarnamn och lösenord som parameter
    public RegistrationDTO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    // Strängrepresentation av registreringsinformation
    public String toString() {
        return "Registration info: username: " + this.username + " password: " + this.password;
    }

    // Getter & Setter för Användarnamn och lösenord
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password  = password;
    }

}
