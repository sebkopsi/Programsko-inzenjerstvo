package com.cookingflamingoz.backend.util;

public class GenericResult {
    public boolean success;
    public String message;

    public GenericResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static GenericResult Success(String message) {
        return new GenericResult(true, message);
    }
    public static GenericResult Failure(String message) {
        return new GenericResult(false, message);
    }
}
