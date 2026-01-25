package com.cookingflamingoz.backend.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @Qualifier("defaultWebClient")
    public WebClient webClient() {
        return  WebClient.builder().build();
    }

    @Bean
    @Qualifier("fileserverWebClient")
    public WebClient fileserverWebClient(){
        return WebClient.builder().baseUrl("http://localhost:9999").build();
    }
}
