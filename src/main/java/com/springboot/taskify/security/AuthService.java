package com.springboot.taskify.security;

import com.springboot.taskify.dto.LoginRequestDTO;
import com.springboot.taskify.dto.LoginResponseDTO;
import com.springboot.taskify.dto.SignupResponseDTO;
import com.springboot.taskify.model.UserEntity;
import com.springboot.taskify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        UserEntity user = (UserEntity) authenticate.getPrincipal();

        String token = authUtil.generateAccessToken(user);
        return new LoginResponseDTO(token,user.getId());
    }

    public SignupResponseDTO signup(LoginRequestDTO signupRequestDTO) {
        UserEntity user = userRepository.findByUsername(signupRequestDTO.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User Already Exists");
        user = userRepository.save(UserEntity.builder()
                .username(signupRequestDTO.getUsername())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .build());

        return new SignupResponseDTO(user.getId(), user.getUsername());
    }
}
