package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    private final SecretKey secretKey;
    private final long preAuthExpiryMillis;

    public JWTService(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.otp.expiry-minutes:5}") long otpExpiryMinutes) {
        if (otpExpiryMinutes < 1) {
            throw new IllegalArgumentException("app.otp.expiry-minutes must be greater than zero");
        }
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.preAuthExpiryMillis = otpExpiryMinutes * 60_000;
    }

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(secretKey)
                .compact();
    }

    public Long extractUserId(String token) {
        var claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (claims.containsKey("preAuth")) {
            throw new IllegalArgumentException("Pre-authentication tokens are not access tokens");
        }
        return Long.valueOf(claims.getSubject());
    }

    public String generatePreAuthToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .claim("preAuth", true)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + preAuthExpiryMillis))
                .signWith(secretKey)
                .compact();
    }

    public Long parsePreAuthToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Pre-authentication token is required");
        }
        var claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (!Boolean.TRUE.equals(claims.get("preAuth", Boolean.class))) {
            throw new IllegalArgumentException("Not a pre-authentication token");
        }
        return Long.valueOf(claims.getSubject());
    }
}
