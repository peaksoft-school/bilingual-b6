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

    public TestResponse(Long id, String title, String shortDescription, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.isActive = isActive;
    }
}