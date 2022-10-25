package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.PaginationResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public PaginationResponse getTestById(Long id, int page, int size) {
        PaginationResponse paginationResponse = new PaginationResponse();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Question> questions = questionRepository.getTestById(id, pageable);

        List<QuestionResponse> responseList = new ArrayList<>();
        for (Question question :
                questions) {
            QuestionResponse response = new QuestionResponse(question.getId(), question.getTitle());
            responseList.add(response);
        }

        paginationResponse.setQuestionResponses(responseList);
        paginationResponse.setCurrentPage(pageable.getPageNumber() + 1);
        paginationResponse.setTotalPages(questions.getTotalPages());
        return paginationResponse;
    }
}
