package com.springboot.taskify.controller;

import com.springboot.taskify.dto.LoginRequestDTO;
import com.springboot.taskify.dto.LoginResponseDTO;
import com.springboot.taskify.dto.SignupResponseDTO;
import com.springboot.taskify.model.UserEntity;
import com.springboot.taskify.security.AuthService;
import com.springboot.taskify.security.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponse = authService.login(loginRequestDTO);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false) // Set to true in production (HTTPS)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 Days
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.getAccessToken())
                .body(loginResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody LoginRequestDTO signupRequestDTO) {
        return ResponseEntity.ok(authService.signup(signupRequestDTO));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@CookieValue(name = "refreshToken") String refreshToken) {
        LoginResponseDTO loginResponse = authService.refreshToken(refreshToken);

        // We only need to send the new Access Token in the header.
        // The Refresh Token usually stays the same until it expires or we rotate it.
        // If you implemented rotation in AuthService, you would reset the cookie here too.

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.getAccessToken())
                .body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // Delete cookie
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }




}
