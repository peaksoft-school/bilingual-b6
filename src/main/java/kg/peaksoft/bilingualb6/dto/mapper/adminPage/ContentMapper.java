package kg.peaksoft.bilingualb6.dto.mapper.adminPage;

import kg.peaksoft.bilingualb6.dto.request.ContentRequest;
import kg.peaksoft.bilingualb6.entites.Content;
import org.springframework.stereotype.Component;

@Component
public class ContentMapper {

    public Content mapToEntity(ContentRequest contentRequest){
        return Content.builder()
                .content(contentRequest.getContent())
                .contentType(contentRequest.getContentType())
                .build();
    }
}
