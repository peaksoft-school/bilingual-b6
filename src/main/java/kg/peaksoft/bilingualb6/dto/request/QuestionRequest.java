package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

    private String title;

    private Integer duration;

    private QuestionType questionType;

    private Boolean isActive;
}
