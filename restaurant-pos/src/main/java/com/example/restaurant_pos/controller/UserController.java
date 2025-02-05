package com.example.restaurant_pos.controller;


import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public User registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.loginUser(userRequestDTO);
    }
}
