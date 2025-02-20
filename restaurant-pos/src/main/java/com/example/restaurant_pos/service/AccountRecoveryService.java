package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.User;
import com.example.restaurant_pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountRecoveryService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountRecoveryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int generateRecoveryCode(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public void initiateRecovery(String email){
        User user = userRepository.findAllByEmail(email).get();
        if(user == null) {
            throw new RuntimeException("User not found");
        }

        int recoveryCode = generateRecoveryCode();
        user.setRecoveryCode(recoveryCode);
        userRepository.save(user);

        String subject = "Account Recovery Code";
        String message = "Your recovery code is: " + recoveryCode;
        emailService.sendEmail(email, subject, message);
    }

    public void verifyRecoveryCode(String email, int enteredCode){
        User user = userRepository.findAllByEmail(email).get();
        if(user == null) {
            throw new RuntimeException("User not found");
        }

        if(!user.getRecoveryCode().equals(enteredCode) || user.getRecoveryCode() == null) {
            throw new RuntimeException("Incorrect recovery code");
        }

        user.setRecoveryVerified(true);
        userRepository.save(user);
    }

    public void resetPassword(String email, String newPassword){
        User user = userRepository.findAllByEmail(email).get();
        if(user == null) {
            throw new RuntimeException("User not found");
        }

        if(!user.isRecoveryVerified()) {
            throw new RuntimeException("Recovery code not verified. Please verify the code first.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRecoveryCode(null);
        user.setRecoveryVerified(false);
        userRepository.save(user);
    }
}
