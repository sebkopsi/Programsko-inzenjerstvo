package com.cookingflamingoz.backend.controller.user;

public class SignUpRequest {

    private String username;
    private String firstname;
    private String surname;
    private String password;
    private String email;

    public SignUpRequest() {
    }
    public SignUpRequest(String username, String firstname, String surname, String password, String email) {
        this.username = username;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }


    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //toString
    @Override
    public String toString() {
        return "UserEnrolleDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
