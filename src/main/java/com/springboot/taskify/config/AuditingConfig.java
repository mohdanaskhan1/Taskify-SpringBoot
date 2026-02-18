package com.springboot.taskify.config;

import com.springboot.taskify.model.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() ||
                    authentication.getPrincipal().equals("anonymousUser")) {
                return Optional.of("system");
            }

            Object principal = authentication.getPrincipal();

            // Handle your custom UserEntity (JWT flow)
            if (principal instanceof UserEntity user) {
                return Optional.of(user.getUsername());
            }

            // Handle OAuth2 User (Google/Github flow)
            if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User oauthUser) {
                // You can return email or a specific attribute used as username
                String email = oauthUser.getAttribute("email");
                return Optional.of(email != null ? email : oauthUser.getName());
            }

            return Optional.of("system");
        };
    }
}
