package kg.peaksoft.bilingualb6.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TestResponse {
    private Long id;
    private String title;
    private String description;
    private List<QuestionResponse> questionResponses;
}
