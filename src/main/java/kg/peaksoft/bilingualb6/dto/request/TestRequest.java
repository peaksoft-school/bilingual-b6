package kg.peaksoft.bilingualb6.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class TestRequest {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String shortDescription;
}
