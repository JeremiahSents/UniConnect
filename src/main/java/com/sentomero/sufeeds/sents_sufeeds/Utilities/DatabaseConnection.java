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
    private static final String PASSWORD = "";

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            // Fix: Add the proper resource path
            String fxmlPath = "/com/sentomero/sufeeds/Views/" + fxmlFile;
            FXMLLoader loader = new FXMLLoader(DatabaseConnection.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading scene: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void signUp(ActionEvent event, String firstName, String lastName,
                              Date dateOfBirth, int yearOfStudy, String courseOfStudy,
                              String username, String password) {
        String checkUserQuery = "SELECT * FROM Users WHERE username = ?";
        String insertUserQuery = "INSERT INTO Users (first_name, last_name, date_of_birth, " +
                "year_of_study, course_of_study, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // First check if username exists
            try (PreparedStatement checkUserExists = connection.prepareStatement(checkUserQuery)) {
                checkUserExists.setString(1, username);
                ResultSet resultSet = checkUserExists.executeQuery();

                if (resultSet.isBeforeFirst()) {
                    showAlert("Username already exists.", Alert.AlertType.ERROR);
                    return;
                }
            }

            // If username doesn't exist, proceed with insert
            try (PreparedStatement psInsert = connection.prepareStatement(insertUserQuery)) {
                psInsert.setString(1, firstName);
                psInsert.setString(2, lastName);
                psInsert.setDate(3, dateOfBirth);
                psInsert.setInt(4, yearOfStudy);
                psInsert.setString(5, courseOfStudy);
                psInsert.setString(6, username);
                psInsert.setString(7, password); // Consider hashing the password

                int rowsAffected = psInsert.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Registration successful!", Alert.AlertType.INFORMATION);
                    changeScene(event, "HomePage.fxml", username);
                } else {
                    showAlert("Failed to register user.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void loginUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            // Fix: Use the constants defined at the top of the class
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, password);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.next()) {
                // Fix: Use the correct file name and remove SceneManager reference
                changeScene(event, "HomePage.fxml", "Home Page");
            } else {
                showAlert("Invalid credentials!", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error: " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            // Close all resources
            try {
                if (resultSet != null) resultSet.close();
                if (psCheckUserExists != null) psCheckUserExists.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}