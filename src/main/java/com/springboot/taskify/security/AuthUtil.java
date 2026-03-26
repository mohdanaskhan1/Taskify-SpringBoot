package com.springboot.taskify.security;

import com.springboot.taskify.model.UserEntity;
import com.springboot.taskify.model.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKet;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKet.getBytes(StandardCharsets.UTF_8));
    }

    // Set Expiration times
    private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15 Minutes
    private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 Days


    public String generateAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(userEntity.getUsername())
                .claim("userId", userEntity.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(getSecretKey())
                .compact();
    }

    // Generates long-lived Refresh Token
    public String generateRefreshToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(userEntity.getUsername())
                .claim("userId", userEntity.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(getSecretKey())
                .compact();
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }


    public AuthProviderType getProviderTypeFromRegistrationId(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "facebook" -> AuthProviderType.FACEBOOK;
            case "twitter" -> AuthProviderType.TWITTER;
            case "email" -> AuthProviderType.EMAIl;
            default -> throw new IllegalArgumentException("Unsupported OAuth2 provider " + registrationId);
        };
    }

    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
        String providerId = switch (registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported OAuth2provider :{}", registrationId);
                throw new IllegalArgumentException("Unsupported OAuth2provider : " + registrationId);
            }
        };
        if (providerId == null || providerId.isBlank()) {
            log.error("Unable to determine providerId for provider : {}", registrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login");
        }
        return providerId;
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
        String email = oAuth2User.getAttribute("email");
        if (email != null && !email.isBlank()) {
            return email;
        }
        return switch (registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }
}





