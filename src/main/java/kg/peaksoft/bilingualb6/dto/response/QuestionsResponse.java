package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.Content;
import kg.peaksoft.bilingualb6.entites.Option;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsResponse {

    private Long id;

    private String title;

    private Integer duration;

    private String shortDescription;

    private String passage;

    private Integer numberOfReplays;

    private String statement;

    private Content content;

    private Integer minNumberOfWords;

    private List<Option> options;
}
