package com.example.restaurant_pos.repository;

import com.example.restaurant_pos.model.ProductSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductSalesRepository extends JpaRepository<ProductSales, Integer> {

    Optional<ProductSales> findByProductIdAndSaleDate(long productId, LocalDate saleDate);
}
