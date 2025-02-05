package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.Category;
import com.example.restaurant_pos.model.Product;
import com.example.restaurant_pos.model.request.ProductRequestDTO;
import com.example.restaurant_pos.repository.CategoryRepository;
import com.example.restaurant_pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product(productRequestDTO.getName(), productRequestDTO.getPrice(),
                productRequestDTO.getDescription(), productRequestDTO.isAvailable(), category);

        return productRepository.save(product);

    }
}
