package kg.peaksoft.bilingualb6.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionUpdateResponse {

    private String title;

    private String statement;

    private String passage;

    private Integer numberOfReplays;

    private Integer duration;

    private Integer minNumberOfWords;

    private String correctAnswer;

    private String content;

    private List<OptionResponse> options;
}
