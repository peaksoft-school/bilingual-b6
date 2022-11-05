package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public class QuestionRequest {

    private Long testId;

    @NotNull(message = "The title should not be empty")
    @NotBlank
    private String title;

    private Integer duration;

    private String statement;

    private String passage;

    private Integer numberOfReplays;

    private Integer minNumberOfWords;

    private String correctAnswer;

    private ContentRequest contentRequest;

    private OptionType optionType;

    private QuestionType questionType;

    private List<OptionRequest> options;
}
