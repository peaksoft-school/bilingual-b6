package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.TestRequest;
import kg.peaksoft.bilingualb6.dto.response.*;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
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

    public SimpleResponse enableDisable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found!"));
        test.setIsActive(!test.getIsActive());
        String a;
        if (test.getIsActive()) {
            a = "enabled";
        } else {
            a = "disabled" +
                    "";
        }
        return new SimpleResponse(String.format("Test successfully %s", a), "ok");
    }

    public TestInnerPageResponse getTestById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found!"));

        List<QuestionResponseForGetByTestId> questions = questionRepository.getQuestionByTestId(id);
        Integer duration = 0;
        for (QuestionResponseForGetByTestId question : questions) {
            duration += question.getDuration();
        }
        return TestInnerPageResponse.builder()
                .id(test.getId())
                .title(test.getTitle())
                .shortDescription(test.getShortDescription())
                .duration(duration)
                .questionResponses(questions)
                .build();
    }

    public TestResponseGetTestByIdForClient getTestByIdForClient(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Test not found!"));

        List<QuestionResponse> questions = questionRepository.getQuestionByTestIdForClient(id);
        return TestResponseGetTestByIdForClient.builder()
                .id(test.getId())
                .title(test.getTitle())
                .shortDescription(test.getShortDescription())
                .questionResponses(questions)
                .build();
    }

    public SimpleResponse deleteTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException("Test not found!"));
        testRepository.delete(test);
        return new SimpleResponse("Test successfully deleted!", "Ok");
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Test not found!"));
        if (testRequest.getTitle().isEmpty() || testRequest.getShortDescription().isEmpty()) {
            throw new BadRequestException("The question title and description should not be an empty!!!");
        }
        test.setShortDescription(testRequest.getShortDescription());
        test.setTitle(testRequest.getTitle());
        test.setIsActive(test.getIsActive());
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

    public List<TestResponseForClient> getAllTestForClient() {
        List<Test> tests = testRepository.findAllForClient();
        List<TestResponseForClient> responses = new ArrayList<>();
        for (Test t : tests) {
            TestResponseForClient testResponseForClient = new TestResponseForClient();
            testResponseForClient.setTitle(t.getTitle());
            testResponseForClient.setShortDescription(t.getShortDescription());
            testResponseForClient.setId(t.getId());
            int a = 0;
            for (Question q : t.getQuestions()) {
                if (q.getIsActive()) {
                    a += q.getDuration();
                }
            }
            testResponseForClient.setDuration(a);
            responses.add(testResponseForClient);
        }
        return responses;
    }
}