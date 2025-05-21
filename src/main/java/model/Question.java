package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    @JsonProperty("question")
    private String text;

    @JsonProperty("options")
    private List<String> options;

    @JsonProperty("answer")
    private String answer;

    public Question() {}

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }

    /** JSON の "answer" をそのまま返す正解文字列 */
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
