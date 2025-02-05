package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User registerUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getUsername(),
                userRequestDTO.getEmail(),
                userRequestDTO.getPassword());
        return userRepository.save(user);
    }

    public String loginUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.findAllByEmail(userRequestDTO.getEmail());
        if (user != null) {
            if (userRequestDTO.getEmail().equals(user.getEmail())
                    && userRequestDTO.getPassword().equals(user.getPassword())) {
                return "Logged in successfully";
            } else {
                return "Wrong email or password";
            }
        }
        return "User doesnt exist";
    }
}

