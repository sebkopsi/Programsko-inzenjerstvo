package com.cookingflamingoz.backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.security.Key;


@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // TODO: use environment variable in real app
    private final Key key = Keys.hmacShaKeyFor("SuperSecretKeyThatIsAtLeast32Chars!".getBytes());

    public String generateToken(int userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
