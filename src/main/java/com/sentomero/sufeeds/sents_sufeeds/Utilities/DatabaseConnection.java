package com.sentomero.sufeeds.sents_sufeeds.Utilities;

import com.sentomero.sufeeds.sents_sufeeds.Controllers.Homepage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/uniconnect";
    private static final String USER = "root";
    private static final String PASSWORD = "@rem$Adrian123";

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(DatabaseConnection.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUp(ActionEvent event, String username, String password) {
        String checkUserQuery = "SELECT * FROM users WHERE username = ?";
        String insertUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkUserExists = connection.prepareStatement(checkUserQuery);
             PreparedStatement psInsert = connection.prepareStatement(insertUserQuery)) {

            checkUserExists.setString(1, username);
            ResultSet resultSet = checkUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                showAlert("Username already exists.", Alert.AlertType.ERROR);
            } else {
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
                changeScene(event, "HomePage.fxml", username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loginUser(ActionEvent event, String username, String password) {
        String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                changeScene(event, "HomePage.fxml", username);
            } else {
                showAlert("Invalid username or password.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
