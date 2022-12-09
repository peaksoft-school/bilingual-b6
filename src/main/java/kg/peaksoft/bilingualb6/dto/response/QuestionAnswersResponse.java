package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAnswersResponse {
    private Long id;
    private String questionTitle;
    private Integer score;
    private Status status;
    private Boolean seen;
}
