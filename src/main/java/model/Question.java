package model;

import java.util.List;

/**
 * クイズの１問を表すモデル
 */
public class Question {
    /** 問題文 */
    private String question;
    /** 選択肢リスト */
    private List<String> options;
    /** 正解のインデックス（0 から始まる） */
    private int answerIndex;

    // ↓ Jackson でマッピングされるようにフィールド名とゲッターを一致させる

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    /** これが無いと getAnswerIndex() が未定義になります */
    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}
