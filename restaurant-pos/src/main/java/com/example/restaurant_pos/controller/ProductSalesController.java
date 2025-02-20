package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.ProductSales;
import com.example.restaurant_pos.service.ProductSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class ProductSalesController {

    @Autowired
    private ProductSalesService productSalesService;

    @GetMapping("/daily")
    @ResponseBody
    public List<ProductSales> getSalesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return productSalesService.getSalesByDate(date);
    }

    @GetMapping("/top-selling")
    @ResponseBody
    public List<ProductSales> getTopSellingProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return productSalesService.getTopSellingProducts(startDate, endDate);
    }
}
