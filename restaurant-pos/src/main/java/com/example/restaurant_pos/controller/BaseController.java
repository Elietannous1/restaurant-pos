package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public abstract class BaseController {

    public OrderStatus validateOrderStatus(String orderStatus) {

        try {
            return OrderStatus.valueOf(orderStatus);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
