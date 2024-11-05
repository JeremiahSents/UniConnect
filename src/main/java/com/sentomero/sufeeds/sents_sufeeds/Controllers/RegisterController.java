package com.sentomero.sufeeds.sents_sufeeds.Controllers;

import com.sentomero.sufeeds.sents_sufeeds.Models.User;
import com.sentomero.sufeeds.sents_sufeeds.Utilities.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField dobField;
    @FXML private MenuButton courseMenuButton;
    @FXML private MenuButton yearMenuButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Hyperlink loginLink;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final String NAME_PATTERN = "[a-zA-Z\\s]*";
    private static final String DATE_PATTERN = "\\d{2}/\\d{2}/\\d{4}";
    private static final Logger logger = Logger.getLogger(RegisterController.class.getName());

    @FXML
    public void initialize() {
        loadCoursesFromDatabase();
        setupYearMenuButton();
        setupFieldValidations();
    }

    public void loadCoursesFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Updated query to include course_id and department
            String query = "SELECT course_id, course_name, department FROM Courses ORDER BY course_name";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            courseMenuButton.getItems().clear();
            boolean coursesFound = false;

            while (rs.next()) {
                coursesFound = true;
                String courseId = rs.getString("course_id");
                String courseName = rs.getString("course_name");
                String department = rs.getString("department");

                // Create a more informative menu item
                MenuItem item = new MenuItem(String.format("%s - %s (%s)", courseId, courseName, department));

                // When selected, store both the ID and name but show a cleaner display text
                item.setOnAction(e -> {
                    courseMenuButton.setText(courseName);  // Show only the course name in the button
                    courseMenuButton.setUserData(courseId);  // Store the course ID for database operations
                });

                courseMenuButton.getItems().add(item);
            }

            if (!coursesFound) {
                MenuItem noCoursesItem = new MenuItem("No courses available");
                noCoursesItem.setDisable(true);
                courseMenuButton.getItems().add(noCoursesItem);
                logger.warning("No courses found in the database");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to load courses", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load courses from database");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setupYearMenuButton() {
        yearMenuButton.getItems().clear();
        String[] years = {"Year 1", "Year 2", "Year 3", "Year 4"};
        for (String year : years) {
            MenuItem item = new MenuItem(year);
            item.setOnAction(e -> yearMenuButton.setText(year));
            yearMenuButton.getItems().add(item);
        }
    }

    private void setupFieldValidations() {
        setupFirstNameValidation();
        setupLastNameValidation();
        setupDobValidation();
    }

    private void setupFirstNameValidation() {
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(NAME_PATTERN)) {
                firstNameField.setText(oldValue);
            }
        });
    }

    private void setupLastNameValidation() {
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(NAME_PATTERN)) {
                lastNameField.setText(oldValue);
            }
        });
    }

    private void setupDobValidation() {
        dobField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateDateFormat(dobField.getText());
            }
        });
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        if (!validateAllFields()) return;

        if (isUsernameTaken(usernameField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username already exists");
            return;
        }

        if (registerNewUser()) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful!");
            navigateToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Registration failed");
        }
    }

    private boolean validateAllFields() {
        return validateFirstName() &&
                validateLastName() &&
                validateDob() &&
                validateCourseSelection() &&
                validateYearSelection() &&
                validateUsername() &&
                validatePassword();
    }

    private boolean validateFirstName() {
        if (firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "First name is required");
            return false;
        }
        return true;
    }

    private boolean validateLastName() {
        if (lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Last name is required");
            return false;
        }
        return true;
    }

    private boolean validateDob() {
        if (dobField.getText().isEmpty() || !validateDateFormat(dobField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Valid date of birth is required (DD/MM/YYYY)");
            return false;
        }
        return true;
    }

    private boolean validateCourseSelection() {
        if (courseMenuButton.getText().equals("Select Course")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Course selection is required");
            return false;
        }
        return true;
    }

    private boolean validateYearSelection() {
        if (yearMenuButton.getText().equals("Select Year")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Year selection is required");
            return false;
        }
        return true;
    }

    private boolean validateUsername() {
        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username is required");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password is required");
            return false;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match");
            return false;
        }
        if (passwordField.getText().length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 8 characters long");
            return false;
        }
        return true;
    }

    private boolean validateDateFormat(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter date in DD/MM/YYYY format");
            return false;
        }
    }

    private boolean isUsernameTaken(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to check username", e);
        }
        return false;
    }

    private boolean registerNewUser() {
        User newUser = createUserFromFields();
        return registerUser(newUser);
    }

    private User createUserFromFields() {
        return new User(
                firstNameField.getText(),
                lastNameField.getText(),
                dobField.getText(),
                (String) courseMenuButton.getUserData(),  // Get the stored course ID
                yearMenuButton.getText(),
                usernameField.getText(),
                passwordField.getText()
        );
    }

    private boolean registerUser(User user) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (first_name, last_name, date_of_birth, " +
                    "course, year_of_study, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getDateOfBirth());
            // Get the course ID that was stored in userData
            String courseId = (String) courseMenuButton.getUserData();
            pstmt.setString(4, courseId);  // Store course_id instead of course_name
            pstmt.setString(5, user.getYearOfStudy());
            pstmt.setString(6, user.getUsername());
            pstmt.setString(7, user.getPassword());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to register user", e);
            return false;
        }
    }

    @FXML
    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sentomero/sufeeds/sents_sufeeds/login.fxml"));
            Parent loginView = loader.load();
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            Scene scene = new Scene(loginView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to navigate to login page", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login page");
        }
    }
}
