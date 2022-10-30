package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionRequest {

    private String title;

    private String statement;

    private String passage;

    private Boolean isActive;

    private Integer numberOfReplays;

    private Integer duration;

    private Integer minNumberOfWords;

    private String correctAnswer;

    private ContentRequest contentRequest;

    private OptionType optionType;

    private QuestionType questionType;

    private List<OptionRequest> options;
}
