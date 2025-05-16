package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Question;

public class QuizService {
    private final List<Question> allQuestions;

    public QuizService() {
        this.allQuestions = loadAllQuestions();
    }

    /** JSON から全問題を読み込み */
    private List<Question> loadAllQuestions() {
        try (InputStream is = getClass()
            .getClassLoader()
            .getResourceAsStream("data/quizdata.json")) {
            if (is == null) {
                throw new RuntimeException("quizdata.json not found");
            }
            ObjectMapper mapper = new ObjectMapper();
            Question[] arr = mapper.readValue(is, Question[].class);
            return Arrays.asList(arr);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load quiz data", e);
        }
    }

    /**
     * 指定数の問題をランダムに抽出＆選択肢シャッフルして返す
     */
    public List<Question> getShuffledQuestions(int numQuestions) {
        List<Question> copy = new ArrayList<>(allQuestions);
        Collections.shuffle(copy);
        List<Question> picked = copy.subList(0, Math.min(numQuestions, copy.size()));
        picked.forEach(q -> Collections.shuffle(q.getOptions()));
        return picked;
    }
}
