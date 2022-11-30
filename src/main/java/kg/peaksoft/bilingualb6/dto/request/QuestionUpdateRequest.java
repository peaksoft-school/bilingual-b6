package kg.peaksoft.bilingualb6.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionUpdateRequest {

    private String title;

    private String statement;

    private String passage;

    private Integer numberOfReplays;

    private Integer duration;

    private Integer minNumberOfWords;

    private String correctAnswer;

    private String content;

    private List<Long> willDelete;

    private List<Long> willUpdate;
}