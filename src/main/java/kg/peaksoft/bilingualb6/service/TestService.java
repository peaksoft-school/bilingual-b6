package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.TestRequest;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponseForGetById;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
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

    public TestResponseForGetById getTestById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Test with =%s id not " + "found", id)));

        List<QuestionResponse> questions = questionRepository.getQuestionByTestId(id);
        Integer duration = 0;
        for (QuestionResponse question : questions) {
            question.setOptionResponseList(optionRepository.getAllOptionsByQuestionId(question.getId()));
            duration += question.getDuration();
        }
        return TestResponseForGetById.builder()
                .id(test.getId())
                .title(test.getTitle())
                .shortDescription(test.getShortDescription())
                .duration(duration)
                .questionResponses(questions)
                .build();
    }

    public SimpleResponse deleteTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Test with id=%d not found! ", id)));
        testRepository.delete(test);
        return new SimpleResponse(" DELETED ", String.format(" Test with %d id successfully deleted", id));
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Test with %d id not found", id)));
        if (testRequest.getTitle().isEmpty() || testRequest.getShortDescription().isEmpty()){
            throw new BadRequestException("The question title and description should not be an empty!!!");
        }
        test.setShortDescription(testRequest.getShortDescription());
        test.setTitle(testRequest.getTitle());
        test.setIsActive(test.getIsActive());
        testRepository.save(test);
        return new TestResponse(test.getId(), test.getTitle(), test.getShortDescription(), test.getIsActive());
    }

    public List<TestResponse> getAll() {
        return testRepository.getAllTest();
    }


    public TestResponse save(TestRequest request) {
        Test test = new Test(request);
        testRepository.save(test);
        return new TestResponse(test.getId(), test.getTitle(), test.getShortDescription(), test.getIsActive());
    }
}