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

    private List<QuestionAnswer> questionAnswers;

    private Client client;

    private Test test;
}
