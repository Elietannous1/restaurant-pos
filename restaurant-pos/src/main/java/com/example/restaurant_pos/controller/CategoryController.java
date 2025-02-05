package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.Category;
import com.example.restaurant_pos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
