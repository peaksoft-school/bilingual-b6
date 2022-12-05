package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class OptionResponse {

    private Long id;

    private String option;

    private String link;

    private Boolean isTrue;

    public OptionResponse(Long id, String option, String link, Boolean isTrue) {
        this.id = id;
        this.option = option;
        this.link = link;
        this.isTrue = isTrue;
    }

    public OptionResponse(Long id, String option, String link) {
        this.id = id;
        this.option = option;
        this.link = link;
    }
}
