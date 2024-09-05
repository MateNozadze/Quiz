package com.example.loggame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserDao userDao;

    public LoginController() {
        this.userDao = new UserDao();
    }

    @FXML
    protected void loginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userDao.authenticate(username, password);
        if (user != null) {
            showAlert("Login Successful", "Welcome " + user.getUsername());
            Main.switchScene("/game.fxml");
            clearFields();
        } else {
            showAlert("Login Failed", "Invalid username or password");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}
