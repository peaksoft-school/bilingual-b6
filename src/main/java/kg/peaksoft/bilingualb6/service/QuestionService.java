package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public SimpleResponse enableDisable(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));
        question.setIsActive(!question.getIsActive());
        String a;
        if (question.getIsActive()){
            a = "enabled";
        } else {
            a = "disabled";
        }
        return new SimpleResponse(String.format("Question with id = %s is = %s", id, a), "ok");
    }

    public SimpleResponse delete(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));
        if (question != null) {
            questionRepository.updateByIdForDeleteQuestionToContentId(id);
            questionRepository.updateByIdForDeleteQuestionToTestId(id);
            questionRepository.delete(question);
        }
        return new SimpleResponse("deleted", "ok");
    }

    public SimpleResponse update(Long id, QuestionUpdateRequest questionUpdateRequest){
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));
        question.setTitle(questionUpdateRequest.getTitle());
        question.setDuration(questionUpdateRequest.getDuration());
        question.setQuestionType(question.getQuestionType());
        question.setIsActive(question.getIsActive());
        questionRepository.save(question);
        return new SimpleResponse("updated", "ok");
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
