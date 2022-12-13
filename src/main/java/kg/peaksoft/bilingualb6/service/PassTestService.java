package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.PassTestRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionAnswerRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.*;
import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.entites.enums.Status;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class PassTestService {

    private final TestRepository testRepository;

    private final OptionRepository optionRepository;

    private final QuestionRepository questionRepository;

    private final ResultRepository resultRepository;

    private final ClientRepository clientRepository;

    private final QuestionAnswerRepository answerRepository;

    public SimpleResponse passTest(PassTestRequest request, Principal principal) {
        Result result = new Result();
        Client client = clientRepository.findClientByAuthInfoEmail(principal.getName());
        result.setClient(client);
        Test test = testRepository.findById(request.getTestId()).orElseThrow();
        result.setTest(test);
        result.setDateOfSubmission(LocalDateTime.now());
        result.setStatus(Status.NOT_EVALUATED);
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        for (QuestionAnswerRequest answerRequest : request.getQuestionsAnswers()) {
            Question question = questionRepository.findById(answerRequest.getQuestionId()).orElseThrow();

            if (question.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                    question.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
                    question.getQuestionType() == QuestionType.SELECT_BEST_TITLE ||
                    question.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {
                if (question.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                        question.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
                    if (answerRequest.getOptionAnswerId().isEmpty() || answerRequest.getOptionAnswerId() == null) {
                        log.error("Choose the correct answer options!");
                        throw new BadRequestException("Choose the correct answer options!");
                    }
                }
                if (question.getQuestionType() == QuestionType.SELECT_MAIN_IDEA ||
                        question.getQuestionType() == QuestionType.SELECT_BEST_TITLE) {
                    if (answerRequest.getOptionAnswerId().isEmpty() || answerRequest.getOptionAnswerId() == null) {
                        log.error("Choose the correct answer!");
                        throw new BadRequestException("Choose the correct answer");
                    }
                }
                Set<Option> correctOptions = new HashSet<>();
                for (Option option : question.getOptions()) {
                    if (option.getIsTrue().equals(true)) {
                        correctOptions.add(option);
                    }
                }
                int countOfCorrectOption = correctOptions.size();
                correctOptions.clear();
                float scoreForCorrectOption = 10f / countOfCorrectOption;
                int countOfCorrectOptionsUserAnswer = 0;
                int countOfInCorrectOptionsUserAnswer = 0;
                Set<Option> options = new HashSet<>();
                for (Long optionId : answerRequest.getOptionAnswerId()) {
                    Option option = optionRepository.findById(optionId).orElseThrow();
                    if (option.getIsTrue().equals(true)) {
                        countOfCorrectOptionsUserAnswer++;
                    } else countOfInCorrectOptionsUserAnswer++;
                    options.add(option);
                }
                QuestionAnswer questionAnswer;
                if (countOfCorrectOptionsUserAnswer - countOfInCorrectOptionsUserAnswer <= 0) {
                    questionAnswer = new QuestionAnswer(0f, question, new HashSet<>(options), result);
                } else {
                    questionAnswer = new QuestionAnswer(
                            (countOfCorrectOptionsUserAnswer - countOfInCorrectOptionsUserAnswer) * scoreForCorrectOption,
                            question, options, result);
                }
                questionAnswers.add(questionAnswer);
                answerRepository.save(questionAnswer);
            }

            if (question.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR ||
                    question.getQuestionType() == QuestionType.DESCRIBE_IMAGE ||
                    question.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER) {
                if (answerRequest.getAnswer() == null || answerRequest.getAnswer().isEmpty()){
                    log.error("Answer is empty!");
                    throw new BadRequestException("Answer is empty");
                }
                QuestionAnswer questionAnswer = new QuestionAnswer(answerRequest.getAnswer(),
                        0f, question, result);
                answerRepository.save(questionAnswer);
                questionAnswers.add(questionAnswer);
            }

            if (question.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT) {
                if (answerRequest.getAnswer() == null || answerRequest.getAnswer().isEmpty()){
                    log.error("Answer is empty!");
                    throw new BadRequestException("Answer is empty");
                }
                QuestionAnswer questionAnswer = new QuestionAnswer(answerRequest.getAnswer(), 0f,
                        new Content(ContentType.AUDIO, answerRequest.getAnswer()), question, result);
                answerRepository.save(questionAnswer);
                questionAnswers.add(questionAnswer);
            }

            if (question.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS) {
                if (answerRequest.getAnswer() == null || answerRequest.getAnswer().isEmpty()){
                    log.error("Answer is empty!");
                    throw new BadRequestException("Answer is empty");
                }
                StringTokenizer tokenizer = new StringTokenizer(answerRequest.getAnswer());
                if (question.getMinNumberOfWords() > tokenizer.countTokens()) {
                    log.error("Your answer should be minimum %d words");
                    throw new BadRequestException(String.format("Your answer should be minimum %d words",
                            question.getMinNumberOfWords()));
                } else {
                    Integer length = tokenizer.countTokens();
                    QuestionAnswer questionAnswer = new QuestionAnswer(length, answerRequest.getAnswer(),
                            0f, question, result);
                    answerRepository.save(questionAnswer);
                    questionAnswers.add(questionAnswer);
                }
            }
        }
        float finalScore = 0f;
        for (QuestionAnswer questionScore : questionAnswers) {
            finalScore += questionScore.getScore();
        }
        result.setFinalScore(finalScore);
        resultRepository.save(result);
        log.info("Test is complete!");
        return new SimpleResponse("Test is complete!", "PASS");
    }
}