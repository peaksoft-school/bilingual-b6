package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.mapper.adminPage.QuestionMapper;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;

    private final QuestionMapper questionMapper;

    public SimpleResponse save(Long testId, QuestionRequest questionRequest) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new NotFoundException(String.format("Test with id = %s does not exists in database", testId))
        );
        Question question = questionMapper.mapToEntity(questionRequest);
        question.setTest(test);
        questionRepository.save(question);
        return new SimpleResponse("Successfully saved","SAVE");
    }

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
    }
}
