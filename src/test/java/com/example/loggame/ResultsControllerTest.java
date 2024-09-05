package com.example.loggame;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResultsControllerTest {

    @Mock
    private Label resultLabel;

    @Mock
    private VBox resultsPane;

    @InjectMocks
    private ResultsController resultsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetResults() {
        resultsController.setResults(3, 5);
        verify(resultLabel).setText("You have completed the quiz!\nCorrect answers: 3\nTotal questions: 5");
        verify(resultLabel).getStyleClass().add("result-label");
    }

    @Test
    public void testInitialize() {
        when(resultsPane.getStyleClass()).thenReturn((ObservableList<String>) mock(List.class));
        resultsController.initialize();
        verify(resultsPane.getStyleClass()).add("results-pane");
    }
}
