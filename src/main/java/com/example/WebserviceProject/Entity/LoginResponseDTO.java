package com.example.WebserviceProject.Entity;

public class LoginResponseDTO {

    // User relaterad till inloggningen
    private User user;

    // JWT genereras efter lyckad inloggning
    private String jwt;

    //StandardConstructor
    public LoginResponseDTO() {
        super();
    }

    // Constructor med användare och JWT som parameter
    public LoginResponseDTO(User user, String jwt) {
        this.user  = user;
        this.jwt = jwt;
    }

    // Getter & Setter för User & JWT
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt= jwt;
    }
}
