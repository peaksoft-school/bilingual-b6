package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.PassTestRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionAnswerRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.*;
import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.entites.enums.Status;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.ResultRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PassTestService {

    private final TestRepository testRepository;
    private final OptionRepository optionRepository;

    private final QuestionRepository questionRepository;

    private final ResultRepository resultRepository;

    public SimpleResponse passTest(PassTestRequest request) {
        Test test = testRepository.findById(request.getTestId()).orElseThrow(() ->
                new NotFoundException(String.format("Test with %d id not found", request.getTestId())));
        Result result = new Result();
        result.setTest(test);

        int countOfCorrectAnswer = 1;

        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        for (QuestionAnswerRequest answerRequest : request.getQuestionsAnswers()) {
            Question question = questionRepository.findById(answerRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException(String.format("Question with %d id not found", answerRequest.getQuestionId())));
            QuestionAnswer questionAnswer = new QuestionAnswer();
            List<Option> options = new ArrayList<>();

            if (question.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS){
//                    question.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
//                    question.getQuestionType() == QuestionType.SELECT_BEST_TITLE ||
//                    question.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {

                for (Option option : question.getOptions()){
                    if (option.getIsTrue()){
                        System.out.println(countOfCorrectAnswer++);
                        System.out.println(option.getOption());
                    }
                }

                int scoreForCorrectOption = 10/countOfCorrectAnswer;
//                System.out.println(countOfCorrectAnswer);
//                System.out.println(scoreForCorrectOption);
                List<Long> countOfOptionsUserAnswer = answerRequest.getOptionAnswerId().stream().distinct().collect(Collectors.toList());
                int countOfCorrectOptionsUserAnswer = 0;
                int countOfInCorrectOptionsUserAnswer = 0;
                for (Long id : countOfOptionsUserAnswer) {
                    Option option = optionRepository.findById(id).orElseThrow(
                            () -> new NotFoundException(String.format("Option with %d id not found", id)));
                    if (option.getIsTrue()){
                        countOfCorrectOptionsUserAnswer++;
                    } else  {
                        countOfInCorrectOptionsUserAnswer++;
                    }
                    option.setQuestionAnswer(questionAnswer);
//                options.add(option);
                }
                int countOfUserAnswer = countOfCorrectOptionsUserAnswer - countOfInCorrectOptionsUserAnswer;
                if (countOfUserAnswer <= 0){
                    questionAnswer.setScore(0);
                }
                int scoreTheUserCorrectAnswer = scoreForCorrectOption * countOfUserAnswer;
                questionAnswer.setScore(scoreTheUserCorrectAnswer);
                questionAnswer.setQuestion(question);
//            questionAnswer.setOptions(options);
                questionAnswerList.add(questionAnswer);
                questionAnswer.setResult(result);
            }

            if (question.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR ||
                    question.getQuestionType() == QuestionType.DESCRIBE_IMAGE ||
                    question.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER) {
                if (answerRequest.getAnswer().isEmpty() || answerRequest.getAnswer() == null) {
                    throw new BadRequestException("Your answer is empty");
                } else {
                    questionAnswer.setTextResponseUser(answerRequest.getAnswer());
                    questionAnswer.setQuestion(question);
                    questionAnswer.setScore(0);
                    questionAnswerList.add(questionAnswer);
                    questionAnswer.setResult(result);
                }
            }
            if (question.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS) {
                StringTokenizer tokenizer = new StringTokenizer(answerRequest.getAnswer());
                if (question.getMinNumberOfWords() > tokenizer.countTokens()) {
                    throw new BadRequestException(String.format("Your answer should be minimum %d words", question.getMinNumberOfWords()));
                } else {
                    Integer length = tokenizer.countTokens();
                    questionAnswer.setNumberOfWords(length);
                    questionAnswer.setScore(0);
                    questionAnswer.setTextResponseUser(answerRequest.getAnswer());
                    questionAnswer.setQuestion(question);
                    questionAnswer.setResult(result);
                    questionAnswerList.add(questionAnswer);
                }
            }
            if (question.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT) {
                if (answerRequest.getContentRequest().getContent().isEmpty() || answerRequest.getContentRequest().getContent() == null) {
                    throw new BadRequestException("You should record audio!");
                }
                Content content = new Content();
                content.setContentType(ContentType.AUDIO);
                content.setContent(answerRequest.getContentRequest().getContent());
                questionAnswer.setContent(content);
            }
        }
        result.setQuestionAnswers(questionAnswerList);
        result.setDateOfSubmission(LocalDateTime.now());
        int finalScore = 0;
        for (QuestionAnswer questionAnswer : questionAnswerList) {
            finalScore += questionAnswer.getScore();
        }
        result.setFinalScore(finalScore);
        result.setStatus(Status.NOT_EVALUATED);
        resultRepository.save(result);
        return new SimpleResponse("Test is complete!", "PASS");
    }

}
