package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResponseForClient {


    private Long id;

    private String title;

    private String shortDescription;

    private int duration;
}
    private Boolean isActive;

    private Integer duration;

    public TestResponseForClient(Long id, String title, String shortDescription, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.isActive = isActive;
    }
}
