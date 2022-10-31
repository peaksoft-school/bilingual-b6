package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResponse {
    private Long id;
    private String title;
    private String shortDescription;
    private Boolean isActive;
    private List<QuestionResponse> questionResponses;
}
