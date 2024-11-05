package com.sentomero.sufeeds.sents_sufeeds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Updated path to match actual file location
            Parent root = FXMLLoader.load(getClass().getResource("/com/sentomero/sufeeds/Views/Register.fxml"));

            // Set up the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("User Registration");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}