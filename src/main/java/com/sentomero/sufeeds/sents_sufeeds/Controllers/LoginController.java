package com.sentomero.sufeeds.sents_sufeeds.Controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }
}