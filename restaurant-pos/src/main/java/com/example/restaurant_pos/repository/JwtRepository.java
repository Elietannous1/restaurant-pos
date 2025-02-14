package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JwtRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String jwt);
}
