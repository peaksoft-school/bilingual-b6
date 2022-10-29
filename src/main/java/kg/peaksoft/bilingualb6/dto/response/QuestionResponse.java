package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private String title;
    private String passage;
    private Boolean isActive;
    private Integer numberOfReplays;
    private Integer duration;
    private String shortDescription;
    private QuestionType questionType;
    private List<OptionResponse> optionResponseList;
}
