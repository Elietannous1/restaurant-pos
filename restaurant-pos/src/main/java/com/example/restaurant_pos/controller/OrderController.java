package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.Order;
import com.example.restaurant_pos.model.request.OrderRequestDTO;
import com.example.restaurant_pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
/*
TODO:Endpoints Covered:
✅ Create an order
✅ Get all orders
✅ Get order by ID
✅ Get orders for a specific day
✅ Calculate daily profit
✅ Cancel an order
 */

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping("")
    @ResponseBody
    public List<Order> getAllOrders(@RequestParam(required = false) Integer id){
        return orderService.getOrder(id);
    }

    @PostMapping("/create")
    @ResponseBody
    public Order createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/date/day")
    @ResponseBody
    public List<Order> getOrdersForDay(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return orderService.getOrdersByDay(parsedDate);
    }

    @GetMapping("/date/month")
    @ResponseBody
    public List<Order> getOrdersForMonth(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return orderService.getOrdersbyMonth(parsedDate);
    }

    @GetMapping("/income")
    @ResponseBody
    public String getIncome(@RequestParam String date, @RequestParam String period) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        if(period.equalsIgnoreCase("day")){
            return orderService.getDailyIncome(parsedDate);
        } else if (period.equalsIgnoreCase("month")){
            return orderService.getMonthlyIncome(parsedDate);
        } else {
            throw new IllegalArgumentException("Invalid period. Please specify 'day' or 'month'.");
        }

    }
}
