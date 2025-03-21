package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.Token;
import com.example.restaurant_pos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface JwtRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String jwt);
    List<Token> findByUserAndExpiredAndRevoked(User userId, boolean expired, Boolean revoked);
}
