package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.service.AccountRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountRecoveryController {

    @Autowired
    private AccountRecoveryService accountRecoveryService;

    @PostMapping("/recover")
    @ResponseBody
    public String initiateRecovery(@RequestBody UserRequestDTO userRequestDTO) {
        accountRecoveryService.initiateRecovery(userRequestDTO.getEmail());
        return "Recovery code sent successfully";
    }

    @PostMapping("/verify")
    @ResponseBody
    public String verify(@RequestBody UserRequestDTO userRequestDTO, @RequestParam int code) {
        accountRecoveryService.verifyRecoveryCode(userRequestDTO.getEmail(), code);
        return "Recovery code verified successfully.";
    }

    @PostMapping("/reset")
    @ResponseBody
    public String reset(@RequestBody UserRequestDTO userRequestDTO) {
        accountRecoveryService.resetPassword(userRequestDTO.getEmail(), userRequestDTO.getPassword());
        return "Password reset successfully.";
    }

}
