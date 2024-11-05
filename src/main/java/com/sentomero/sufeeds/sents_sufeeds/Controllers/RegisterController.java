package com.sentomero.sufeeds.sents_sufeeds.Controllers;

import com.sentomero.sufeeds.sents_sufeeds.Utilities.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField dobField;
    @FXML
    private MenuButton courseMenuButton;
    @FXML
    private MenuButton yearMenuButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Define course options
        String[][] courses = {
                {"CSCI101", "Bachelor of Computer Science and Informatics", "School of Computing and Engineering (SCES)"},
                {"CYBN101", "Bachelor of Cybersecurity and Computer Networks", "School of Computing and Engineering (SCES)"},
                {"BBIT101", "Bachelor of Business Information Technology", "School of Computing and Engineering (SCES)"},
                {"FENG101", "Bachelor of Financial Engineering", "School of Mathematical Sciences (SIMS)"},
                {"FECO101", "Bachelor of Financial Economics", "School of Mathematical Sciences (SIMS)"},
                {"HHM101", "Bachelor of Hospitality and Hotel Management", "School of Humanities"},
                {"BCOM101", "Bachelor of Commerce", "School of Humanities"},
                {"IR101", "Bachelor of International Relations", "School of Humanities"}
        };

        // Populate course dropdown menu
        for (String[] course : courses) {
            String courseId = course[0];
            String courseName = course[1];
            String department = course[2];

            MenuItem courseItem = new MenuItem(String.format("%s - %s (%s)", courseId, courseName, department));
            courseItem.setOnAction(e -> {
                courseMenuButton.setText(courseName);  // Display the course name on the button
                courseMenuButton.setUserData(courseId);  // Store the course ID in userData
            });
            courseMenuButton.getItems().add(courseItem);
        }

        // Populate year dropdown menu
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        for (String year : years) {
            MenuItem yearItem = new MenuItem(year);
            yearItem.setOnAction(e -> yearMenuButton.setText(year));  // Display the selected year on the button
            yearMenuButton.getItems().add(yearItem);
        }

        // Register button action
        registerButton.setOnAction(event -> {
            String selectedCourseId = (String) courseMenuButton.getUserData();  // Retrieve the selected course ID
            String selectedYear = yearMenuButton.getText();  // Retrieve the selected year

            if (selectedCourseId == null || selectedYear.equals("Select Year")) {
                showAlert("Please select both a course and a year.", Alert.AlertType.WARNING);
            } else {
                System.out.println("Selected Course ID: " + selectedCourseId);
                System.out.println("Selected Year: " + selectedYear);
                // Additional code to save user data, including selected course and year, to the database
            }
        });

        if (!usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {
            DatabaseConnection.signUp(new ActionEvent(), usernameField.getText(), passwordField.getText());
        } else {
            showAlert("Please fill in all necessary details.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}