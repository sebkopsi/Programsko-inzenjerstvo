package com.cookingflamingoz.backend.model;

public class UserCreationResult {
    private boolean success;
    private String message;
    private User user;
    // TODO: add some integer status to easily identify error types ?

    // Constructors
    public UserCreationResult(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // some methods for readability
    public static UserCreationResult success(User user) {
        return new UserCreationResult(true, "User created successfully", user);
    }

    public static UserCreationResult failure(String message) {
        return new UserCreationResult(false, message, null);
    }
}
