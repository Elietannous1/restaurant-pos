package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Product,Integer> {

    Optional<Product> findById(long id);
}
