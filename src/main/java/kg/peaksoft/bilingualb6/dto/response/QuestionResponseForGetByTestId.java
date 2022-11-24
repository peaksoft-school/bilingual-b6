package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseForGetByTestId {

    private Long id;

    private String title;

    private Integer duration;

    private QuestionType questionType;

    private Boolean isActive;
}
