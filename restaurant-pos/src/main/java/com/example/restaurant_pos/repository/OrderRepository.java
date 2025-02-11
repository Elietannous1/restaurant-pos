package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}
