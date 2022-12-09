package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ViewResultResponse {

    private Long id;

    private String userFullName;

    private LocalDateTime dateOfSubmission;

    private Status status;

    private Integer finalScore;

    private String testTitle;

    private List<QuestionAnswersResponse> questionAnswers;

    public ViewResultResponse(Long id, String userFullName, LocalDateTime dateOfSubmission, Status status, Integer finalScore, String testTitle) {
        this.id = id;
        this.userFullName = userFullName;
        this.dateOfSubmission = dateOfSubmission;
        this.status = status;
        this.finalScore = finalScore;
        this.testTitle = testTitle;
    }
}
