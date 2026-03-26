package com.springboot.taskify.repository;

import com.springboot.taskify.model.UserEntity;
import com.springboot.taskify.model.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}
