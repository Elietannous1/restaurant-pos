package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.Category;
import com.example.restaurant_pos.model.request.CategoryRequestDTO;
import com.example.restaurant_pos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category(categoryRequestDTO.getCategoryName(), categoryRequestDTO.getDescription());
        return categoryRepository.save(category);
    }

    public String deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).get();
        if(category != null) {
            categoryRepository.delete(category);
            return "Category deleted";
        }
        return "Category not found";
    }
}
