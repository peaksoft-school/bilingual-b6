package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class OptionResponse {

    private Long id;

    private String option;

    private String title;

    private Boolean isTrue;

    public OptionResponse(Long id, String option, String title, Boolean isTrue) {
        this.id = id;
        this.option = option;
        this.title = title;
        this.isTrue = isTrue;
    }

    public OptionResponse(Long id, String option, String title) {
        this.id = id;
        this.option = option;
        this.title = title;
    }
}
