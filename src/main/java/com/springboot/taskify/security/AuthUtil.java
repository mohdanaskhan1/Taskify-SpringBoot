package com.springboot.taskify.security;

import com.springboot.taskify.model.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKet;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKet.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(userEntity.getUsername())
                .claim("userId",userEntity.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis() + 10000*60*10))
                .signWith(getSecretKey())
                .compact();
    }


}
