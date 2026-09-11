package com.r3vtech.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//public class JwtService {
//
//}
//package com.r3treading.auth.service;
//
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration:3600000}")
    private long accessTokenExpiration;


    // =========================
    // Secret Key
    // =========================

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }


    // =========================
    // Generate Access Token
    // =========================

    public String generateAccessToken(String userId, String email) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + accessTokenExpiration
        );

        return Jwts.builder()
                .subject(userId)
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }


    // =========================
    // Extract User ID
    // =========================

    public String extractUserId(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }


    // =========================
    // Extract Email
    // =========================

    public String extractEmail(String token) {

        return extractClaim(
                token,
                claims -> claims.get("email", String.class)
        );
    }


    // =========================
    // Extract Expiration
    // =========================

    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }


    // =========================
    // Extract Any Claim
    // =========================

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }


    // =========================
    // Extract All Claims
    // =========================

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // =========================
    // Check Expired
    // =========================

    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }


    // =========================
    // Validate Token
    // =========================

    public boolean isTokenValid(
            String token,
            String userId) {

        try {

            String extractedUserId =
                    extractUserId(token);

            return extractedUserId.equals(userId)
                    && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }


    // =========================
    // Token Expiration
    // =========================

    public long getAccessTokenExpiration() {

        return accessTokenExpiration;
    }
}