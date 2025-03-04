package com.example.restaurant_pos.frontend.controller;

import com.example.restaurant_pos.frontend.controller.utils.ScenePaths;
import com.example.restaurant_pos.frontend.controller.utils.SceneSwitcher;
import com.example.restaurant_pos.frontend.controller.utils.TokenManager;
import com.example.restaurant_pos.model.request.JwtDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    private final String REGISTER_URL = "http://localhost:8080/user/register"; // Adjust based on your backend URL

    @FXML
    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate the input fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required!");
            errorLabel.setVisible(true);
            return;
        }

        // Prepare the JSON payload for registration
        String requestBody = "{\"username\":\"" + username + "\", \"email\":\"" + email + "\", \"password\":\"" + password + "\"}";
        System.out.println("RequestBody is: " + requestBody);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP request
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Send POST request to the backend
            ResponseEntity<JwtDTO> response = restTemplate.postForEntity(REGISTER_URL, request, JwtDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JwtDTO jwtDTO = response.getBody();
                TokenManager tokenManager = new TokenManager(jwtDTO.getToken(), jwtDTO.getRefreshToken());
                // Registration successful
                errorLabel.setText("Registration successful!");
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setVisible(true);

                Stage stage = (Stage) errorLabel.getScene().getWindow();

                SceneSwitcher.switchScene(stage, ScenePaths.DASHBOARD_VIEW, ScenePaths.DASHBOARD_CSS);
            } else {
                // Handle other non-2xx statuses (e.g., 400 Bad Request)
                errorLabel.setText("Registration failed: " + response.getBody());
                errorLabel.setVisible(true);
            }

        } catch (Exception e) {
            // Handle exceptions (e.g., server connection issues)
            System.out.println("Error: " + e.getMessage());
            errorLabel.setText("Email address is invalid!");
            errorLabel.setVisible(true);
        }
    }
}
