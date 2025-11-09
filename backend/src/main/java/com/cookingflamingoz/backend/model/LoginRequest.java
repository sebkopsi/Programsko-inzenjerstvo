package com.cookingflamingoz.backend.model;

public class LoginRequest {
    private String email; // NOTE: email or username
    private String password;

    // Constructors
    LoginRequest () {}
    LoginRequest (String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}