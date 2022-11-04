package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestInnerPageResponse {

    private Long id;

    private String title;

    private String shortDescription;

    private List<QuestionResponse> questionResponses;

    private Integer duration;
}