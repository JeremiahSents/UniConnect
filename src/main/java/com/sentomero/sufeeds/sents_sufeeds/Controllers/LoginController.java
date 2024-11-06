package com.sentomero.sufeeds.sents_sufeeds.Controllers;

import com.sentomero.sufeeds.sents_sufeeds.Utilities.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink registerLink;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                DatabaseConnection.showAlert("Please fill in all fields!", Alert.AlertType.ERROR);
                return;
            }
            try {
                DatabaseConnection.loginUser(event, usernameField.getText(), passwordField.getText());
            } catch (Exception e) {
                DatabaseConnection.showAlert("Login failed: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        registerLink.setOnAction(event -> {
            DatabaseConnection.changeScene(event, "com/sentomero/sufeeds/Views/Register.fxml", "Registration");
        });
    }
}
