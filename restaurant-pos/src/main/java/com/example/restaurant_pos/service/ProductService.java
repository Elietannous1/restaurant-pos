package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.Category;
import com.example.restaurant_pos.model.Product;
import com.example.restaurant_pos.model.request.ProductRequestDTO;
import com.example.restaurant_pos.model.response.ProductResponseDTO;
import com.example.restaurant_pos.repository.CategoryRepository;
import com.example.restaurant_pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getProductName(),
                        product.getPrice(),
                        product.getDescription(),
                        product.isAvailable(),
                        product.getCategory() != null ? product.getCategory().getCategoryName() : null
                ))
                .collect(Collectors.toList());
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).get();
    }

    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product(productRequestDTO.getProductName(), productRequestDTO.getPrice(),
                productRequestDTO.getDescription(), productRequestDTO.isAvailable(), category);

        return productRepository.save(product);

    }

    public String removeProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            return "Product removed successfully";
        }
        return "Product not found";
    }

    public Product updateProduct(Integer id, ProductRequestDTO productRequestDTO) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).get();
            product.get().setProductName(productRequestDTO.getProductName());
            product.get().setPrice(productRequestDTO.getPrice());
            product.get().setDescription(productRequestDTO.getDescription());
            product.get().setCategory(category);
            product.get().setAvailable(productRequestDTO.isAvailable());
            productRepository.save(product.get());
            return product.get();
        }
        return null;
    }
}
