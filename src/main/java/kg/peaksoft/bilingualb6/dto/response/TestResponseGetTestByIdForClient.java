package kg.peaksoft.bilingualb6.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TestResponseGetTestByIdForClient {


    private Long id;

    private String title;

    private String shortDescription;

    private List<QuestionResponse> questionResponses;

    private Integer duration;
}
