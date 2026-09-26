package com.example.loggame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main extends Application {




    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        switchScene("/login.fxml");
    }

    public static void switchScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | NullPointerException e) {
            logger.error("Failed to load scene: {}", fxml, e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load screen: " + fxml);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {

        try {
            // ეს ჩაურთვის H2-ის ვებ სერვერს პორტზე 8082
            org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 Console started at http://localhost:8082");
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
