package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public SimpleResponse enableDisable(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));
        question.setIsActive(!question.getIsActive());
        String a;
        if (question.getIsActive()) {
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

    public SimpleResponse update(Long id, QuestionRequest questionRequest){
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));
        question.setTitle(questionRequest.getTitle());
        question.setDuration(questionRequest.getDuration());
        question.setQuestionType(questionRequest.getQuestionType());
        question.setIsActive(questionRequest.getIsActive());
        questionRepository.save(question);
        return new SimpleResponse("updated", "ok");
    }
}
