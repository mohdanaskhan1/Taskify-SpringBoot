package com.springboot.taskify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;
    private Long userId;
}
