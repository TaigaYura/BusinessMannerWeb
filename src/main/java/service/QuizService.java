package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Question;

public class QuizService {

    private final List<Question> allQuestions;

    public QuizService() {
        this.allQuestions = loadAllQuestions();
    }

    private List<Question> loadAllQuestions() {
        InputStream is = getClass().getClassLoader()
                         .getResourceAsStream("data/quizdata.json");
        if (is == null) {
            throw new RuntimeException("quizdata.json not found in classpath");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(is, new TypeReference<List<Question>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load quiz data", e);
        }
    }

    public List<Question> getShuffledQuestions(int questionsPerRound) {
        if (allQuestions == null || allQuestions.isEmpty()) {
            throw new RuntimeException("No quiz questions loaded");
        }
        List<Question> copy = new ArrayList<>(allQuestions);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(questionsPerRound, copy.size()));
    }
}
