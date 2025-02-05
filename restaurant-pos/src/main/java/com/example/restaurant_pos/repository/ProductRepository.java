package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Integer> {
}
