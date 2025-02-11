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
}
