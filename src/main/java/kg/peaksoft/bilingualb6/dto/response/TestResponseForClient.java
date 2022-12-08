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
