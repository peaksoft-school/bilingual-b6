package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.entites.QuestionAnswer;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ResultResponses {

    private Long id;

    private LocalDateTime dateOfSubmission;

    private Status status;

    private Integer finalScore;

    private Client client;

    private Test test;

    private List<QuestionAnswer> questionAnswers;

    public ResultResponses(Long id, LocalDateTime dateOfSubmission,
                           Status status, Integer finalScore, Client client, Test test) {
        this.id = id;
        this.dateOfSubmission = dateOfSubmission;
        this.status = status;
        this.finalScore = finalScore;
        this.client = client;
        this.test = test;
    }
}
