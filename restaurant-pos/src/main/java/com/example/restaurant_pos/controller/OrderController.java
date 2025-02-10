package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.Order;
import com.example.restaurant_pos.model.request.OrderRequestDTO;
import com.example.restaurant_pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    @ResponseBody
    public Order createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("")
    @ResponseBody
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }


}
