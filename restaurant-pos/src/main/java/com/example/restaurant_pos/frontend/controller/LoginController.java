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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    private final String LOGIN_URL = "http://localhost:8080/user/login"; // Adjust based on your backend URL

    @FXML
    private void loginUser() {
        // Clear any previous error messages
        errorLabel.setVisible(false);

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password cannot be empty!");
            errorLabel.setVisible(true);
            return;
        }

        // Prepare the request payload
        String requestBody = "{\"email\":\"" + username + "\", \"password\":\"" + password + "\"}";
        System.out.println("RequestBody is: " + requestBody);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP request
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Send POST request
            ResponseEntity<JwtDTO> response = restTemplate.postForEntity(LOGIN_URL, request, JwtDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JwtDTO jwtDTO = response.getBody();

                TokenManager.getInstance().setToken(jwtDTO.getToken());
                TokenManager.getInstance().setRefreshToken(jwtDTO.getRefreshToken());
                System.out.println("Access Token: " + jwtDTO.getToken());
                System.out.println("Refresh Token: " + jwtDTO.getRefreshToken());

                // Successful login
                errorLabel.setText("Login successful!");
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setVisible(true);

                //Redirect the user to the dashboard
                Stage stage = (Stage) errorLabel.getScene().getWindow();
                SceneSwitcher.switchScene(stage, ScenePaths.DASHBOARD_VIEW, ScenePaths.DASHBOARD_CSS);
            } else {
                // Handle non-2xx status codes
                errorLabel.setText("Invalid credentials!");
                errorLabel.setVisible(true);
            }

        } catch (HttpClientErrorException e) {
            // Handle 4xx errors (e.g., 403 Forbidden, 400 Bad Request)
            System.out.println("HTTP Error Code: " + e.getStatusCode().value());
            System.out.println("Error Message: " + e.getMessage());

            if (e.getStatusCode().value() == 403) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Wrong credentials!");
            } else if (e.getStatusCode().value() == 400) {
                errorLabel.setText("Bad Request: Please check your credentials!");
            } else {
                errorLabel.setText("Client error occurred: " + e.getMessage());
            }
            errorLabel.setVisible(true);

        } catch (Exception e) {
            // Handle other exceptions
            System.out.println("Error: " + e.getMessage());
            errorLabel.setText("Error connecting to server!");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void goToRegisterView() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        SceneSwitcher.switchScene(stage, ScenePaths.REGISTER_VIEW, ScenePaths.REGISTER_CSS);
    }
}
