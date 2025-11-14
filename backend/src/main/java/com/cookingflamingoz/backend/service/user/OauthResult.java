package com.cookingflamingoz.backend.service.user;

public class OauthResult {
    public boolean success;
    public String message;
    public String email;
    public String firstname;
    public String lastname;
    public String username;

    private OauthResult(boolean success, String message, String email, String firstname, String lastname, String username) {
        this.success = success;
        this.message = message;
        this.email = email;
        this.firstname =  firstname;
        this.lastname = lastname;
        this.username = username;
    }


    static OauthResult Success(String email, String firstname, String lastname, String username) {
        return new OauthResult(true, "", email, firstname, lastname, username);
    }
    
    static OauthResult Failure(String message){
        return new OauthResult(false, message, null, null, null, null);
    }
}
