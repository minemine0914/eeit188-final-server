package com.ispan.eeit188_final.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    // Extract user ID from the token
    public String extractUserId(String token) {
        return extractAllClaims(token).get("id").toString();
    }

    // Extract user role from the token
    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role").toString();
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extract claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token (you can add role-based checks here if needed)
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false; // Invalid signature, token tampered
        }
    }
}
