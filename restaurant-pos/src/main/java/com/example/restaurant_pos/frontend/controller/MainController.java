package com.example.restaurant_pos.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea; // The main content area where views will be loaded

    @FXML
    private void loadDashboard() {
        loadView("/com/restaurantpos/views/dashboard-view.fxml");
    }

    @FXML
    private void loadOrders() {
        loadView("/com/restaurantpos/views/orders-view.fxml");
    }

    @FXML
    private void loadMenuManagement() {
        loadView("/com/restaurantpos/views/menu-management-view.fxml");
    }

    @FXML
    private void loadSettings() {
        loadView("/com/restaurantpos/views/settings-view.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurantpos/views/user-views/main-views/dashboard-view.fxml"));
            Parent view = loader.load();
            contentArea.getChildren().clear(); // Clear previous view
            contentArea.getChildren().add(view); // Load new view
        } catch (IOException e) {
            System.out.println("Error loading view: " + e.getMessage());
        }
    }
}
