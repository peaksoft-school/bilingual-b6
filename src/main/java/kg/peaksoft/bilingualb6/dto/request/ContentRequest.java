package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentRequest {

    private ContentType contentType;

    private String content;
}
