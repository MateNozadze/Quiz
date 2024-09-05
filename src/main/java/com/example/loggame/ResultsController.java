package com.example.loggame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ResultsController {

    @FXML
    private Label resultLabel;

    @FXML
    private VBox resultsPane;

    public void setResults(int correctCount, int totalQuestions) {
        String resultText = "You have completed the quiz!\n" +
                "Correct answers: " + correctCount + "\n" +
                "Total questions: " + totalQuestions;

        resultLabel.setText(resultText);
        resultLabel.getStyleClass().add("result-label");
    }

    @FXML
    public void initialize() {
        if (resultsPane != null) {
            resultsPane.getStyleClass().add("results-pane");
        } else {
            System.out.println("resultsPane is null.");
        }
    }
}
