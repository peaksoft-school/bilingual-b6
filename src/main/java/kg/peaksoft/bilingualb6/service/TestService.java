package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.TestRequest;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponse;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public SimpleResponse enableDisable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Test with =%s id not " +
                        "found", id)));
        test.setIsActive(!test.getIsActive());
        String a;
        if (test.getIsActive()) {
            a = "enabled";
        } else {
            a = "disabled" +
                    "";
        }
        return new SimpleResponse(String.format("Test with = %s id is = %s", id, a), "ok");
    }


    public TestResponse getTestById(Long id) {

        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Test with =%s id not " + "found", id)));

        List<QuestionResponse> questions = questionRepository.getQuestionByTestId(id);
        Integer duration = 0;
        for (QuestionResponse question : questions) {
            question.setOptionResponseList(optionRepository.getAllOptionsByQuestionId(question.getId()));
            duration += question.getDuration();
        }
        return TestResponse.builder()
                .id(test.getId())
                .title(test.getTitle())
                .shortDescription(test.getShortDescription())
                .duration(duration)
                .isActive(test.getIsActive())
                .questionResponses(questions)
                .build();
    }

    public SimpleResponse deleteTest(Long id) {
        testRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Test with id=%d not found! ", id)));
        testRepository.deleteById(id);
        return new SimpleResponse(" DELETED ", String.format(" Test with %d id successfully deleted", id));
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Test with %d id not found", id)));
        test.setShortDescription(testRequest.getShortDescription());
        test.setTitle(testRequest.getTitle());
        test.setIsActive(testRequest.getIsActive());
        testRepository.save(test);
        return new TestResponse(test.getId(),test.getTitle(),test.getIsActive(),test.getShortDescription());
    }

    public List<TestResponse> getAll() {
        List<TestResponse> responses = new ArrayList<>();
        for (Test test : testRepository.findAll()) {
            responses.add(new TestResponse(test.getId(),test.getTitle(),test.getIsActive(),test.getShortDescription()));
        }
        return responses;
    }

    public TestResponse save(TestRequest request) {
        Test test = Test.builder()
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .isActive(request.getIsActive())
                .build();
        testRepository.save(test);
        return new TestResponse(test.getId(),test.getTitle(),test.getIsActive(),test.getShortDescription());
    }
}