package com.cookingflamingoz.backend.service.user;

import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.util.GenericResult;

public class UserProfileResult extends GenericResult {
    private User user;
    public String firstname;
    public String surname;

    UserProfileResult(boolean success, String message, User user) {
        super(success, message);
        if(user != null){
            this.user = user;
            this.firstname = user.getFirstname();
            this.surname = user.getSurname();
        }
    }

    public static UserProfileResult UserProfileResultSuccess(User user){
        return new UserProfileResult(true, "User profile fetched successfully.", user);
    }

    public static UserProfileResult UserProfileResultFailure(String message){
        return new UserProfileResult(false, message, null);
    }
}
