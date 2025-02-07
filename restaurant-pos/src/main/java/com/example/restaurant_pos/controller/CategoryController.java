package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.Category;
import com.example.restaurant_pos.model.Product;
import com.example.restaurant_pos.model.request.CategoryRequestDTO;
import com.example.restaurant_pos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    @ResponseBody
    public List<Category> getCategory() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/")
    @ResponseBody
    public Category getCategoryById(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/create")
    @ResponseBody
    public Category createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.createCategory(categoryRequestDTO);
    }

    @PostMapping("/delete/")
    @ResponseBody
    public String deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/products/")
    @ResponseBody
    public List<Product> getAllProductsFromCategory(@RequestParam Integer id) {
        return categoryService.getProductsFromCategory(id);
    }


}
