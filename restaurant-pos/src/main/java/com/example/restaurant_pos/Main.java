package com.example.restaurant_pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main extends Application {

    public static void main(String[] args) {
        // Start JavaFX UI
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an FXMLLoader with the correct resource path
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurantpos/views/main-view.fxml"));

        // Load the FXML file
        Parent root = loader.load();

        // Set the scene with the loaded root
        primaryStage.setScene(new Scene(root));

        // Set the title of the stage
        primaryStage.setTitle("JavaFX & Spring Boot");

        // Show the stage
        primaryStage.show();
    }


}
