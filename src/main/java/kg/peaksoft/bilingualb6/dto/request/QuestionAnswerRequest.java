package kg.peaksoft.bilingualb6.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAnswerRequest {

    private Long questionId;

    private List<Long> optionAnswerId;

    private String answer;

    private Integer numberOfPlays;

}
