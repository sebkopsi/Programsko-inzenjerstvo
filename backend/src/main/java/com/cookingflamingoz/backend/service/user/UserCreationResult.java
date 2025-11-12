package com.cookingflamingoz.backend.service.user;

import com.cookingflamingoz.backend.model.User;

public class UserCreationResult {
    private boolean success;
    private String message;
    private int userID;
    // TODO: add some integer status to easily identify error types ?

    // Constructors
    public UserCreationResult(boolean success, String message, int userID) {
        this.success = success;
        this.message = message;
        this.userID = userID;
    }

    //getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    // some methods for readability
    public static UserCreationResult success(User user) {
        return new UserCreationResult(true, "User created successfully", user.getId());
    }

    public static UserCreationResult failure(String message) {
        return new UserCreationResult(false, message, 0);
    }
}
