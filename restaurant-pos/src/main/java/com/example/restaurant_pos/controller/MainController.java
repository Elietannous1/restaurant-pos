package com.example.restaurant_pos.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    Button button;

    @FXML
    public void handleButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Button Clicked");
        alert.setHeaderText(null);
        alert.setContentText("Hello from JavaFX!");
        alert.showAndWait();
    }
}
