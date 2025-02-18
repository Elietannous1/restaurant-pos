package com.example.restaurant_pos.controller;


import com.example.restaurant_pos.model.request.JwtDTO;
import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.service.EmailValidationService;
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

    @Autowired
    EmailValidationService emailValidationService;

    @PostMapping("/register")
    @ResponseBody
    public JwtDTO registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getEmail().isEmpty() || userRequestDTO.getPassword().isEmpty()
                || userRequestDTO.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Email, username, and password must be provided.");
        }

        if (!emailValidationService.isEmailValid(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email address is invalid.");
        }

        return userService.registerUser(userRequestDTO);
    }


    @PostMapping("/login")
    @ResponseBody
    public JwtDTO loginUser(@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        if(userRequestDTO.getEmail().isEmpty())
            throw new Exception("Invalid email");

        return userService.loginUser(userRequestDTO);
    }

    @PostMapping("/refresh-token")
    @ResponseBody
    public JwtDTO refreshToken(@RequestBody UserRequestDTO userRequestDTO){

       return userService.refreshToken(userRequestDTO);
    }
}
