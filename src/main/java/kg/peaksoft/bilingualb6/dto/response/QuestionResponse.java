package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuestionResponse {

    private Long id;

    private String title;

    private String passage;

    private Boolean isActive;

    private String correctAnswer;

    private Integer numberOfReplays;

    private Integer minNumberOfWords;

    private String content;

    private Integer duration;

    private String shortDescription;

    private QuestionType questionType;

    private String statement;

    private List<OptionResponse> optionResponseList;

    public QuestionResponse(Long id, String title, String passage, Boolean isActive,
                            Integer numberOfReplays, Integer duration, String content,
                            QuestionType questionType, String statement, Integer minNumberOfWords,
                            String correctAnswer) {
        this.id = id;
        this.title = title;
        this.passage = passage;
        this.isActive = isActive;
        this.numberOfReplays = numberOfReplays;
        this.duration = duration;
        this.content = content;
        this.questionType = questionType;
        this.statement = statement;
        this.minNumberOfWords = minNumberOfWords;
        this.correctAnswer = correctAnswer;
    }
}