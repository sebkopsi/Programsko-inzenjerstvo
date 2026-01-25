package com.cookingflamingoz.backend.service.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;


@Service
public class OauthService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private WebClient webClient;

    public OauthResult GoogleOauth(String token) {
        String googleTokenInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
        Map<String, Object> tokenInfo;
        try {
           tokenInfo = webClient.get().uri(googleTokenInfoUrl).header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}).block();
        } catch (Exception e) {
            return OauthResult.Failure(e.getMessage());
        }
        if(tokenInfo == null || tokenInfo.get("email") == null ) {
            return OauthResult.Failure("Failed to get Oauth info from Google with provided token");
        }

        String email = tokenInfo.getOrDefault("email", "").toString();
        String firstname = tokenInfo.getOrDefault("first_name", "").toString();
        String lastname = tokenInfo.getOrDefault("family_name", "").toString();
        String username = tokenInfo.getOrDefault("name", "").toString();

        return OauthResult.Success(email, firstname, lastname, username);
    }
}
