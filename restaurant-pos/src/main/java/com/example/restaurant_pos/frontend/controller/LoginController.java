package com.example.restaurant_pos.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
            ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_URL, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Successful login
                errorLabel.setText("Login successful!");
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setVisible(true);
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
        try {
            // Load the register view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/restaurantpos/views/user-views/register-view.fxml"));
            VBox registerView = fxmlLoader.load();

            // Create a new scene and set it in the stage
            Scene scene = new Scene(registerView);
            Stage stage = (Stage) errorLabel.getScene().getWindow(); // Get the current window
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Error loading Register view!");
            errorLabel.setVisible(true);
        }
    }
}
