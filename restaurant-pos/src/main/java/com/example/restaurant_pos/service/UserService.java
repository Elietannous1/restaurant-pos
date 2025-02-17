package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User registerUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getUsername(),
                userRequestDTO.getEmail(),
                passwordEncoder.encode(userRequestDTO.getPassword()));
        return userRepository.save(user);
    }

    public String loginUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.findAllByEmail(userRequestDTO.getEmail());
        if (user != null) {
            if (passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword())) {
                return "Logged in successfully";
            } else {
                return "Wrong email or password";
            }
        }
        return "User doesnt exist";
    }
}

