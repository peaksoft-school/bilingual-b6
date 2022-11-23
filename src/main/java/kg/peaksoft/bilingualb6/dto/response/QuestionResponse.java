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
    private Integer numberOfReplays;
    private Integer duration;

    private String shortDescription;
    private QuestionType questionType;
    private String statement;

    private List<OptionResponse> optionResponseList;

    public QuestionResponse(Long id, String title, String passage, Boolean isActive,
                            Integer numberOfReplays, Integer duration,
                            QuestionType questionType, String statement) {
        this.id = id;
        this.title = title;
        this.passage = passage;
        this.isActive = isActive;
        this.numberOfReplays = numberOfReplays;
        this.duration = duration;
        this.questionType = questionType;
        this.statement = statement;
    }
}