package kg.peaksoft.bilingualb6.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationRequest {

    private Long questionAnswerId;

    private Float score;
}
