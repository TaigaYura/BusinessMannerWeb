package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Question;

/**
 * QuizService は quizdata.json から問題を読み込み、
 * 指定数分シャッフルして返します。
 */
public class QuizService {
    // 全問題リスト
    private final List<Question> allQuestions;

    /**
     * コンストラクタで一度だけ全問題を読み込み
     */
    public QuizService() {
        this.allQuestions = loadAllQuestions();
    }

    /**
     * 指定数の問題をランダムに取り出し、選択肢もシャッフルして返す
     * @param numQuestions ランダム取得する問題数
     * @return シャッフル済みの問題リスト
     */
    public List<Question> getShuffledQuestions(int numQuestions) {
        // 全体をコピーしてシャッフル
        List<Question> copy = new ArrayList<>(allQuestions);
        Collections.shuffle(copy);
        // 要求数をリストサイズに合わせる
        if (numQuestions > copy.size()) {
            numQuestions = copy.size();
        }
        // サブリストを取得
        List<Question> picked = new ArrayList<>(copy.subList(0, numQuestions));
        // 各問題の選択肢もシャッフル
        for (Question q : picked) {
            Collections.shuffle(q.getOptions());
        }
        return picked;
    }

    /**
     * JSON ファイルから全問題を読み込む
     * @return JSON から読み込んだ問題リスト
     */
    private List<Question> loadAllQuestions() {
        // クラスパスから data/quizdata.json を読み込む
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("data/quizdata.json")) {
            if (is == null) {
                throw new RuntimeException("quizdata.json not found");
            }
            // Jackson ObjectMapper でパース
            ObjectMapper mapper = new ObjectMapper();
            Question[] questions = mapper.readValue(is, Question[].class);
            return Arrays.asList(questions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load quiz data", e);
        }
    }
}
