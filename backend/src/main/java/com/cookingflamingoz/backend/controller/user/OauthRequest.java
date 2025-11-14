package com.cookingflamingoz.backend.controller.user;

public class OauthRequest {
    private String token;
    private String serviceType;

    public OauthRequest(String token, String serviceType) {
        this.token = token;
        this.serviceType = serviceType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}