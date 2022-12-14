package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewResultResponse {

    private Long id;

    private String userFullName;

    private String testTitle;

    private LocalDateTime dateOfSubmission;

    private Float finalScore;

    private Status finalStatus;

    private List<QuestionAnswerResponse> questionAnswerResponses;

}
