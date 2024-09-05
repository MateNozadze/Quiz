package com.example.loggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static Logger logger = LogManager.getLogger(GameController.class);

    @FXML
    Label feedbackLabel;
    @FXML
    Label questionLabel;
    @FXML
    Button option1;
    @FXML
    Button option2;
    @FXML
    Button option3;
    @FXML
    Button nextButton;
    @FXML
    Label timerLabel;
    @FXML
    VBox questionPane;
    @FXML
    Button finishButton;

    List<Question> questions;
    int currentQuestionIndex = 0;
    int correctCount = 0;
    private boolean quizCompleted = false;
    private Timeline timer;
    private int timeLeft = 60;
    private Object stage;

    @FXML
    public void initialize() {
        logger.info("Initializing GameController...");
        questions = loadQuestions();
        showQuestion();
        hideFeedbackLabel();
        startTimer();
    }

    @FXML
    private void handleOption1() {
        handleAnswer(option1.getText().substring(3));
    }

    @FXML
    private void handleOption2() {
        handleAnswer(option2.getText().substring(3));
    }

    @FXML
    private void handleOption3() {
        handleAnswer(option3.getText().substring(3));
    }

    @FXML
    void handleNext() {
        if (!quizCompleted) {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                showQuestion();
                resetTimer();
            } else {
                quizCompleted = true;
                finishButton.setVisible(true);
                nextButton.setVisible(false);
                stopTimer();
            }
        }
    }

    @FXML
    private void handleFinish() {
        showResults();
    }

    @FXML
    void handleAnswer(String selectedAnswer) {
        if (!quizCompleted) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            String correctAnswer = currentQuestion.getCorrectAnswer();

            if (selectedAnswer.equals(correctAnswer)) {
                correctCount++;
                logger.info("Correct answer selected.");
            } else {
                logger.info("Incorrect answer selected.");
            }

            handleNext();
        }
    }

    void startTimer() {
        logger.info("Starting timer...");
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            if (timeLeft <= 0) {
                timer.stop();
                if (!quizCompleted) {
                    handleNext();
                }
            } else {
                timerLabel.setText("Time left: " + timeLeft + "s");
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            logger.info("Timer stopped.");
        }
    }

    private void resetTimer() {
        timeLeft = 60;
        stopTimer();
        startTimer();
    }

    void showQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        String questionText = (currentQuestionIndex + 1) + ". " + currentQuestion.getQuestion();
        questionLabel.setText(questionText);

        List<String> options = currentQuestion.getOptions();
        option1.setText("a) " + options.get(0));
        option2.setText("b) " + options.get(1));
        option3.setText("c) " + options.get(2));
        hideFeedbackLabel();
        logger.info("Displayed question: " + questionText);
    }

    void showResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results.fxml"));
            VBox resultsPane = loader.load();
            ResultsController resultsController = loader.getController();
            resultsController.setResults(correctCount, questions.size());

            Scene scene = new Scene(resultsPane);
            scene.getStylesheets().add(getClass().getResource("/result.css").toExternalForm());

            Stage stage = (Stage) questionPane.getScene().getWindow();
            stage.setScene(scene);
            logger.info("Quiz results displayed.");
        } catch (IOException e) {
            logger.error("Error displaying results: ", e);
        }
    }

    void hideFeedbackLabel() {
        feedbackLabel.setVisible(false);
    }

    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is 2 + 2?", "4", List.of("3", "4", "5")));
        questions.add(new Question("What is the capital of France?", "Paris", List.of("London", "Berlin", "Paris")));
        questions.add(new Question("Which planet is known as the Red Planet?", "Mars", List.of("Earth", "Mars", "Jupiter")));
        logger.info("Questions loaded.");
        return questions;
    }

}
