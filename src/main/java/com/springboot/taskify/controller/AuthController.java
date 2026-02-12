package com.springboot.taskify.controller;

import com.springboot.taskify.dto.LoginRequestDTO;
import com.springboot.taskify.dto.LoginResponseDTO;
import com.springboot.taskify.dto.SignupResponseDTO;
import com.springboot.taskify.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody LoginRequestDTO signupRequestDTO){
        return ResponseEntity.ok(authService.signup(signupRequestDTO));
    }


}
