package com.springboot.taskify.repository;

import com.springboot.taskify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
