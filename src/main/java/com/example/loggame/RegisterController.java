package com.example.loggame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private final UserDao userDao = new UserDao();

    @FXML
    protected void registerButtonAction(ActionEvent event) {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String confirmPassword = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();

        if (username.isBlank() || password.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Missing information", "Please fill in all fields.");
            return;
        }
        if (username.length() < MIN_USERNAME_LENGTH) {
            showAlert(Alert.AlertType.WARNING, "Invalid username",
                    "Username must be at least " + MIN_USERNAME_LENGTH + " characters long.");
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            showAlert(Alert.AlertType.WARNING, "Weak password",
                    "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.WARNING, "Passwords don't match", "Please make sure both passwords are identical.");
            return;
        }

        UserDao.RegistrationResult result = userDao.register(username, password);
        switch (result) {
            case SUCCESS -> {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You can now log in with your new account.");
                clearFields();
                Main.switchScene("/login.fxml");
            }
            case USERNAME_TAKEN -> showAlert(Alert.AlertType.WARNING, "Username Taken",
                    "This username is already in use. Please choose another one.");
            case ERROR -> showAlert(Alert.AlertType.ERROR, "Registration Failed", "Something went wrong. Please try again.");
        }
    }

    @FXML
    protected void goToLoginAction(ActionEvent event) {
        clearFields();
        Main.switchScene("/login.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
