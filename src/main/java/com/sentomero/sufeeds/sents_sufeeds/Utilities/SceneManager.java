package com.sentomero.sufeeds.sents_sufeeds.Utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
        public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
            try {
                // Using the correct path structure for your project
                String fxmlPath = "/com/sentomero/sufeeds/Views/" + fxmlFile;
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load FXML file: " + e.getMessage());
            }
        }
    }

