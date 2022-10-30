package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.entites.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OptionRequest {

    private String option;

    private Boolean isTrue;

}
