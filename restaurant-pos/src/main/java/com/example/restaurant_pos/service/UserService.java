package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();

    }

    public User registerUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getUsername(),
                             userRequestDTO.getEmail(),
                             userRequestDTO.getPassword());
        return userRepository.save(user);
    }

}
