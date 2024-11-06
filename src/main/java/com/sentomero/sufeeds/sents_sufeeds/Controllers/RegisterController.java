package com.sentomero.sufeeds.sents_sufeeds.Controllers;

import com.sentomero.sufeeds.sents_sufeeds.Utilities.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            yearItem.setOnAction(e -> yearMenuButton.setText(year));
            yearMenuButton.getItems().add(yearItem);
        }

        // Register button action
        registerButton.setOnAction(this::handleRegistration);
    }

    private void handleRegistration(ActionEvent event) {
        // Validate all fields are filled
        if (!validateFields()) {
            showAlert("Please fill in all necessary details.", Alert.AlertType.ERROR);
            return;
        }

        // Validate passwords match
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Passwords do not match.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Parse date of birth
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dobField.getText(), formatter);
            Date dateOfBirth = Date.valueOf(localDate);

            // Get year of study as integer (convert "1st Year" to 1, etc.)
            String yearText = yearMenuButton.getText().replace("st Year", "")
                    .replace("nd Year", "")
                    .replace("rd Year", "")
                    .replace("th Year", "");
            int yearOfStudy = Integer.parseInt(yearText);

            // Get course of study
            String courseOfStudy = courseMenuButton.getText();

            // Call the database connection to register the user
            DatabaseConnection.signUp(
                    event,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    dateOfBirth,
                    yearOfStudy,
                    courseOfStudy,
                    usernameField.getText(),
                    passwordField.getText()
            );

        } catch (DateTimeParseException e) {
            showAlert("Invalid date format. Please use YYYY-MM-DD format.", Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Invalid year of study.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Registration error: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        return !firstNameField.getText().trim().isEmpty() &&
                !lastNameField.getText().trim().isEmpty() &&
                !dobField.getText().trim().isEmpty() &&
                !courseMenuButton.getText().equals("Select Course") &&
                !yearMenuButton.getText().equals("Select Year") &&
                !usernameField.getText().trim().isEmpty() &&
                !passwordField.getText().trim().isEmpty() &&
                !confirmPasswordField.getText().trim().isEmpty();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}