package kg.peaksoft.bilingualb6.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateContentRequest {
    private Long id;
    private String content;
}
