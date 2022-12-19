package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.TestRequest;
import kg.peaksoft.bilingualb6.dto.response.*;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class TestService {

    private final TestRepository testRepository;

    private final QuestionRepository questionRepository;

    private final OptionRepository optionRepository;

    public SimpleResponse enableDisable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Test with id: " + id + "not found!");
                    throw new NotFoundException("Test with id: " + id + "not found!");
                });
        test.setIsActive(!test.getIsActive());
        String a;
        if (test.getIsActive()) {
            a = "enabled";
            log.info("Test with id: " + id + "enabled");
        } else {
            a = "disabled" +
                    "";
            log.info("Test with id:" + id + "disabled");
        }
        log.info("Test with id: {}" + id + "successfully %s", a);
        return new SimpleResponse(String.format("Test with id: " + id + "successfully %s", a), "ok");
    }

    public TestInnerPageResponse getTestById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Test with id: " + id + "not found!");
                    throw new NotFoundException("Test with id: " + id + "not found!");
                });

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
                () -> {
                    log.error("Test with id: " + id + "not found!");
                    throw new NotFoundException("Test with id: " + id + "not found!");
                });

        List<QuestionResponse> questions = questionRepository.getQuestionByTestIdForClient(id);
        for (QuestionResponse question : questions) {
            question.setOptionResponseList(optionRepository.getOptions(question.getId()));
        }
        return TestResponseGetTestByIdForClient.builder()
                .id(test.getId())
                .title(test.getTitle())
                .shortDescription(test.getShortDescription())
                .questionResponses(questions)
                .build();
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
                if (q.getIsActive().equals(true)) {
                    a += q.getDuration();
                }
            }
            testResponseForClient.setDuration(a);
            responses.add(testResponseForClient);
        }
        return responses;
    }

    public SimpleResponse deleteTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> {
            log.error("Test with id: " + id + "not found!");
            throw new NotFoundException("Test with id: " + id + "not found!");
        });
        testRepository.delete(test);
        log.info("Test with id:" + id + "successfully deleted!");
        return new SimpleResponse("Test with id: " + id + "successfully deleted!", "Ok");
    }

    public TestResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(() -> {
            log.error("Test with id: " + id + "not found!");
            throw new NotFoundException(("Test with id: " + id + "not found!"));
        });
        if (testRequest.getTitle().isEmpty() || testRequest.getShortDescription().isEmpty()) {
            log.error("The question title and description should not be an empty!");
            throw new BadRequestException("The question title and description should not be an empty!!!");
        }
        test.setShortDescription(testRequest.getShortDescription());
        test.setTitle(testRequest.getTitle());
        test.setIsActive(test.getIsActive());
        log.info("Test with id:" + id + "Successfully updated!");
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