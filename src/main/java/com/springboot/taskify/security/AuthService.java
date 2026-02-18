package com.springboot.taskify.security;

import com.springboot.taskify.dto.LoginRequestDTO;
import com.springboot.taskify.dto.LoginResponseDTO;
import com.springboot.taskify.dto.SignupResponseDTO;
import com.springboot.taskify.model.UserEntity;
import com.springboot.taskify.model.type.AuthProviderType;
import com.springboot.taskify.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        return new LoginResponseDTO(token, user.getId());
    }

    public UserEntity signupInternal(LoginRequestDTO signupRequestDTO,
                                     AuthProviderType authProviderType,
                                     String providerId) {
        UserEntity user = userRepository.findByUsername(signupRequestDTO.getUsername()).orElse(null);
        if (user != null) throw new IllegalArgumentException("User Already Exists");

        user = UserEntity.builder()
                .username(signupRequestDTO.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .role("ROLE_USER")
                .build();

        if (authProviderType == AuthProviderType.EMAIl) {
            user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        }
        return userRepository.save(user);

    }


    //LoginController
    public SignupResponseDTO signup(LoginRequestDTO signupRequestDTO) {
        UserEntity user = signupInternal(signupRequestDTO, AuthProviderType.EMAIl, null);
        return new SignupResponseDTO(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        //fetch providerType and providerId
        //save providerType and providerId info with user.
        //if the user has an account
        //otherwise signup -> login

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        UserEntity user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");
        UserEntity emailUser = userRepository.findByUsername(email).orElse(null);

        if (user == null && emailUser == null) {
            //signUp Flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signupInternal(new LoginRequestDTO(username, null), providerType, providerId);
        } else if (user != null) {
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
                userRepository.save(user);
            }
        } else {
            throw new BadCredentialsException("This email is already register with provider " + emailUser.getProviderId());
        }
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginResponseDTO);
    }
}
