package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckQuestionAnswerResponse {

    private Long id;

    private String fullName;

    private String testTitle;

    private String questionTitle;

    private Integer duration;

    private QuestionType questionType;

    private Float scoreOfQuestion;

    private List<OptionResponse> options;

    private Integer minNumberOfReplays;

    private String link;

    private String correctAnswer;

    private String statement;

    private Integer minNumberOfWords;

    private String passage;

    private List<OptionResponse> userOptionsAnswer;

    private String userAnswer;

    private Integer userNumberOfPlays;

    public CheckQuestionAnswerResponse(List<OptionResponse> options, List<OptionResponse> userOptionsAnswer) {
        this.options = options;
        this.userOptionsAnswer = userOptionsAnswer;
    }

    public CheckQuestionAnswerResponse(Integer minNumberOfReplays, String link, String correctAnswer, String userAnswer, Integer userNumberOfPlays) {
        this.minNumberOfReplays = minNumberOfReplays;
        this.link = link;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.userNumberOfPlays = userNumberOfPlays;
    }

    public CheckQuestionAnswerResponse(String link, String correctAnswer, String userAnswer) {
        this.link = link;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public CheckQuestionAnswerResponse(Float scoreOfQuestion, String link, String correctAnswer, String userAnswer) {
        this.scoreOfQuestion = scoreOfQuestion;
        this.link = link;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public CheckQuestionAnswerResponse(String statement, Integer minNumberOfWords, String userAnswer, Integer userNumberOfPlays) {
        this.statement = statement;
        this.minNumberOfWords = minNumberOfWords;
        this.userAnswer = userAnswer;
        this.userNumberOfPlays = userNumberOfPlays;
    }

    public CheckQuestionAnswerResponse(String correctAnswer, String statement, String passage, String userAnswer) {
        this.correctAnswer = correctAnswer;
        this.statement = statement;
        this.passage = passage;
        this.userAnswer = userAnswer;
    }

    public CheckQuestionAnswerResponse(List<OptionResponse> options, String passage, List<OptionResponse> userOptionsAnswer) {
        this.options = options;
        this.passage = passage;
        this.userOptionsAnswer = userOptionsAnswer;
    }
}
