package com.sentomero.sufeeds.sents_sufeeds.Controllers;

import com.sentomero.sufeeds.sents_sufeeds.Utilities.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class Homepage implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button feedButton;
    @FXML
    private Button createButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button closeButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

  closeButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
          DatabaseConnection.changeScene(actionEvent,"Login.fxml","Register Page");
      }
  });
    }
    public void setWelcomeLabel(String username){
        welcomeLabel.setText("Welcome " + username + "!");
    }
}
