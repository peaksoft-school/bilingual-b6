package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ResultResponses {

    private Long id;

    private LocalDateTime dateOfSubmission;

    private Status status;

    private Integer finalScore;

    private String testTitle;

    private Long testId;

    public ResultResponses(Long id, LocalDateTime dateOfSubmission,
                           Status status, Integer finalScore, String testTitle, Long testId) {
        this.id = id;
        this.dateOfSubmission = dateOfSubmission;
        this.status = status;
        this.finalScore = finalScore;
        this.testTitle = testTitle;
        this.testId = testId;
    }
}