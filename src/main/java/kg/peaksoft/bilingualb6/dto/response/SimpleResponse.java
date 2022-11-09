package kg.peaksoft.bilingualb6.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SimpleResponse {

    private String message;

    private String status;
}