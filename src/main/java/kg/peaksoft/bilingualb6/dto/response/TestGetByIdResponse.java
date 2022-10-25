package kg.peaksoft.bilingualb6.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TestGetByIdResponse {

    private List<QuestionResponse> questionResponses;

}
