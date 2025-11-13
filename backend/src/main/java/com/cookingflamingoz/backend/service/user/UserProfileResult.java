package com.cookingflamingoz.backend.service.user;

import com.cookingflamingoz.backend.model.User;

public class UserProfileResult {
    private boolean success;
    private String message;

    private User user;
    public String firstname;
    public String surname;

    UserProfileResult(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        if(user != null){
            this.user = user;
            this.firstname = user.getFirstname();
            this.surname = user.getSurname();
        }
    }

    public static UserProfileResult UserProfileResultSuccess(User user){
        return new UserProfileResult(true, null, user);
    }

    public static UserProfileResult UserProfileResultFailure(String message){
        return new UserProfileResult(false, message, null);
    }
}
