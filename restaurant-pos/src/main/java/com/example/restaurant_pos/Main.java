package com.example.restaurant_pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
    public void start(Stage stage) throws Exception {
        // Create an FXMLLoader with the correct resource path
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/restaurantpos/views/user-views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        stage.setTitle("Restaurant POS - Login");
        stage.setScene(scene);
        stage.show();
    }


}
