package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.mapper.adminPage.QuestionMapper;
import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
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

        if (questionRequest.getTitle().isEmpty() || questionRequest.getDuration() == 0) {
            throw new BadRequestException("The title and duration of the question should not be empty");
        }

        if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS && questionRequest.getOptions().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD && questionRequest.getOptions().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getOptions().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getOptions().isEmpty()) {
            throw new BadRequestException("Add at least one option!");
        }

        if (questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getPassage().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getPassage().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getPassage().isEmpty()) {
            throw new BadRequestException("There should be no empty passage in this question!");
        }

        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR && questionRequest.getCorrectAnswer().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.DESCRIBE_IMAGE && questionRequest.getCorrectAnswer().isEmpty()) {
            throw new BadRequestException("In this question should not be empty field <Correct answer>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR && questionRequest.getNumberOfReplays() == 0) {
            throw new BadRequestException("In this question should not be empty or equal to zero field <Number of replays>!");
        }

        if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT && questionRequest.getStatement().isEmpty()){
            throw new BadRequestException("In this question should not be empty field <Statement>!!!");
        }

        if(questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS && questionRequest.getStatement().isEmpty()){
            throw new BadRequestException("In this question should not be empty field <Question statement>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS && questionRequest.getMinNumberOfWords() == 0){
            throw new BadRequestException("In this question should not be empty or equal to zero field <Minimum of words>!!!");
        }

        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getStatement().isEmpty()){
            throw new BadRequestException("In this question should not be empty field <Question statement to the Passage>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getCorrectAnswer().isEmpty()){
            throw new BadRequestException("The field <Highlight correct answer> should not be empty!");
        }

        if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
                questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE) {
            int numberOfTrueOptions = 0;
            for (OptionRequest optionRequest : questionRequest.getOptions()) {
                if (optionRequest.getIsTrue()) {
                    numberOfTrueOptions++;
                }
            }
            if (numberOfTrueOptions > 1) {
                questionRequest.setOptionType(OptionType.MULTIPLE_CHOICE);
            } else if (numberOfTrueOptions == 1) {
                questionRequest.setOptionType(OptionType.SINGLE_CHOICE);
            } else if (numberOfTrueOptions == 0) {
                throw new BadRequestException("Add at least one or two correct options!");
            }
        }

        Question question = questionMapper.mapToEntity(questionRequest);
        question.setTest(test);
        questionRepository.save(question);
        return new SimpleResponse("Successfully saved", "SAVE");
    }

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

    public SimpleResponse update(Long id, QuestionUpdateRequest questionUpdateRequest) {
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
