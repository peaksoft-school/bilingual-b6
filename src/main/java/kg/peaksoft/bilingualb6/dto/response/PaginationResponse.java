package kg.peaksoft.bilingualb6.dto.response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PaginationResponse {
    private List<QuestionResponse> questionResponses;
    private int currentPage;
    private int totalPages;
}
