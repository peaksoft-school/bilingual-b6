package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ViewAllResultResponse {

    private Long id;

    private String userFullName;

    private LocalDateTime dateOfSubmission;

    private String testTitle;

    private Status status;

    private Float finalScore;
}
