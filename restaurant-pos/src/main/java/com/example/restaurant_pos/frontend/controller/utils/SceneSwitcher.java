package com.example.restaurant_pos.frontend.controller.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    public static void switchScene(Stage currentStage, String fxmlFile, String cssFile){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            if (cssFile != null && !cssFile.isEmpty()) {
                String css = SceneSwitcher.class.getResource(cssFile).toExternalForm();
                scene.getStylesheets().add(css);
            }

            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading scene: " + fxmlFile);
        }

    }
}
