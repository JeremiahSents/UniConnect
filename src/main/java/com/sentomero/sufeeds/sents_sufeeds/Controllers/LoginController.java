package com.sentomero.sufeeds.sents_sufeeds.Controllers;


import com.sentomero.sufeeds.sents_sufeeds.Utilities.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
@FXML
    private Label usernameLabel;
    @FXML
private  Label passwordLabel;
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
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DatabaseConnection.loginUser(actionEvent,usernameField.getText(),passwordField.getText());
            }
        });
   registerLink.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           DatabaseConnection.changeScene(event, "/com/sentomero/sufeeds/Views/Register.fxml", "null");
       }
   });

    }
}