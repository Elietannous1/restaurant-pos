package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.Product;
import com.example.restaurant_pos.model.request.ProductRequestDTO;
import com.example.restaurant_pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    @ResponseBody
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    @ResponseBody
    public Product addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.addProduct(productRequestDTO);
    }

    @PostMapping("/remove")
    @ResponseBody
    public String removeProduct(@RequestParam Integer id) {
        return productService.removeProduct(id);
    }

}
