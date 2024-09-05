package com.example.loggame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class GameControllerTest extends ApplicationTest {

    private GameController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        VBox root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Optionally, you can reset or reinitialize the controller state here if needed.
    }

    @Test
    public void testInitialQuestion() {
        // Verify the initial question is displayed correctly
        verifyThat("#questionLabel", LabeledMatchers.hasText("1. What is 2 + 2?"));
    }



    @Test
    public void testTimerInitialization() {
        // Verify the initial timer value is correct
        verifyThat("#timerLabel", LabeledMatchers.hasText("Time left: 60s"));
    }

    @Test
    public void testTimerCountdown() {
        // Wait for the timer to update (requires a sleep or a waiting mechanism)
        sleep(1000); // Sleep for 1 second
        // Verify the timer label has been updated
        verifyThat("#timerLabel", LabeledMatchers.hasText("Time left: 59s"));
    }



}
