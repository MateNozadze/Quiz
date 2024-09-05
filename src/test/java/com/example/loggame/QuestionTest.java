package com.example.loggame;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void testQuestionCreation() {
        Question question = new Question("What is 2 + 2?", "4", List.of("3", "4", "5"));

        assertEquals("What is 2 + 2?", question.getQuestion());
        assertEquals("4", question.getCorrectAnswer());
        assertEquals(3, question.getOptions().size());
        assertTrue(question.getOptions().contains("4"));
    }
}
