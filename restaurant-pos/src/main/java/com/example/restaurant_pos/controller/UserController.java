package com.example.restaurant_pos.controller;


import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    @ResponseBody
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}
