package com.cookingflamingoz.backend.controller.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
