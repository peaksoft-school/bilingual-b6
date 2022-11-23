package kg.peaksoft.bilingualb6.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class OptionResponse {
    private Long id;
    private String option;
    private Boolean isTrue;

    public OptionResponse(Long id, String option, Boolean isTrue) {
        this.id = id;
        this.option = option;
        this.isTrue = isTrue;
    }

    public OptionResponse(Long id, String option) {
        this.id = id;
        this.option = option;
    }
}
