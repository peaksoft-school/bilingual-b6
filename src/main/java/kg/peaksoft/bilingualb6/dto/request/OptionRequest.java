package kg.peaksoft.bilingualb6.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OptionRequest {

    private String option;

    private String link;

    private Boolean isTrue;

}
