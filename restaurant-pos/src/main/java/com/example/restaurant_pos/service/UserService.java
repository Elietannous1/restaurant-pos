package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.Token;
import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.model.request.JwtDTO;
import com.example.restaurant_pos.model.request.UserRequestDTO;
import com.example.restaurant_pos.repository.JwtRepository;
import com.example.restaurant_pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtDTO registerUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getUsername(),
                userRequestDTO.getEmail(),
                passwordEncoder.encode(userRequestDTO.getPassword()));
        userRepository.save(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Token token = new Token(jwtService.generateToken(user), userRepository.findAllByEmail(user.getEmail()).get());
        jwtRepository.save(token);
        return new JwtDTO(token.getToken(), refreshToken);
    }

    public JwtDTO loginUser(UserRequestDTO userRequestDTO){
        User user = userRepository.findAllByEmail(userRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), userRequestDTO.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        revokeToken(user);
        Token token = new Token(jwtService.generateToken(user), userRepository.findAllByEmail(user.getEmail()).get());
        String refreshToken = jwtService.generateRefreshToken(user);
        jwtRepository.save(token);
        return new JwtDTO(token.getToken(), refreshToken);

    }

    public JwtDTO refreshToken(UserRequestDTO userRequestDTO){
        User user = userRepository.findAllByEmail(userRequestDTO.getEmail()).get();
        Token token = new Token();

        if(jwtService.isTokenValid(userRequestDTO.getRefreshToken(), user)){
            revokeToken(user);
            token = new Token(jwtService.generateToken(user), userRepository.findAllByEmail(user.getEmail()).get());
            jwtRepository.save(token);
        }

        JwtDTO jwtDTO = new JwtDTO(token.getToken(),userRequestDTO.getRefreshToken());
        return jwtDTO;
    }

    public void revokeToken(User user){
        List<Token> tokens = jwtRepository.findByUserAndExpiredAndRevoked(user, false, false);
        if(tokens.size() > 0) {
            for (Token token : tokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
        }
        jwtRepository.saveAll(tokens);
    }
}