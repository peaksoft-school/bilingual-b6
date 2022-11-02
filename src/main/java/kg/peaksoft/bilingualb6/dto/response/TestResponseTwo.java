package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResponseTwo {

    private Long id;

    private String title;

    private String shortDescription;

    private Boolean isActive;

    public TestResponseTwo(Long id, String title, String shortDescription) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
    }

    public TestResponseTwo(Long id, String title, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.isActive = isActive;
    }
}