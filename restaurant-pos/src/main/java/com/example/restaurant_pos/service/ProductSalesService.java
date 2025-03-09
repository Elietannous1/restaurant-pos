package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.ProductSales;
import com.example.restaurant_pos.repository.ProductSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductSalesService {

    @Autowired
    private ProductSalesRepository productSalesRepository;

    public List<ProductSales> getSalesByDate(LocalDate startDate, LocalDate endDate) {
        LocalDate finalEndDate;
        if (endDate == null) {
            finalEndDate = startDate;
        } else {
            finalEndDate = endDate;
        }
        return productSalesRepository.findAll().stream()
                .filter(sales -> !sales.getSaleDate().isBefore(startDate) && !sales.getSaleDate().isAfter(finalEndDate))
                .toList();
    }

    public List<ProductSales> getTopSellingProducts(LocalDate startDate, LocalDate endDate) {
        return productSalesRepository.findAll().stream()
                .filter(sales -> !sales.getSaleDate().isBefore(startDate) && !sales.getSaleDate().isAfter(endDate))
                .sorted((s1, s2) -> Integer.compare(s2.getQuantitySold(), s1.getQuantitySold()))
                .toList();
    }
}
