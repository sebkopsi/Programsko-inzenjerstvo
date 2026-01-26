package com.cookingflamingoz.backend.configuration;

import com.cookingflamingoz.backend.service.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final String[] PUBLIC = {
            "/user/login",
            "/user/oauth",
            "/user"
    };

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors().and() // enable CORS
            .csrf(csrf -> csrf.disable()) // disable CSRF
            .authorizeHttpRequests(authorize -> authorize
                    // allow public endpoints
                    .requestMatchers(PUBLIC).permitAll()
                    // allow OPTIONS preflight requests
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // any other request requires authentication
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 13, 3);
    }
}
