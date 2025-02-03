package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findAllByEmail(String email);
}
