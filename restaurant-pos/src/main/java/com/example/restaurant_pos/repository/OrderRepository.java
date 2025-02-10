package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
