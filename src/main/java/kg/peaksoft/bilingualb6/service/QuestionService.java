package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.PaginationResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Content;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;

    public SimpleResponse save(QuestionRequest questionRequest) {
        Test test = testRepository.findById(questionRequest.getTestId()).orElseThrow(
                () -> new NotFoundException(String.format("Test with id=" + questionRequest.getTestId() + " does not exists in database"))
        );
        if (questionRequest.getDuration().equals(0) || questionRequest.getDuration() == null) {
            throw new BadRequestException("The duration should not equal to zero or not be an empty!");
        }

        if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null) {
            throw new BadRequestException("Add at least one option!");
        }
        if (questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getPassage().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getPassage().isEmpty()) {
            throw new BadRequestException("There should be no empty passage in this question!");
        }
        if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
                questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE) {

            if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS && questionRequest.getContentRequest().getContentType() != ContentType.TEXT ||
                    questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getContentRequest().getContentType() != ContentType.TEXT ||
                    questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getContentRequest().getContentType() != ContentType.TEXT) {
                throw new BadRequestException("The question option type should be the <TEXT> format!");
            }
            if (questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD && questionRequest.getContentRequest().getContentType() != ContentType.AUDIO) {
                throw new BadRequestException("The question option type should be the <AUDIO> format!");
            } else {
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
                if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS || questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
                    Question question = questionRepository.save(new Question(questionRequest));
                    question.setTest(test);
                }
                if (questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE || questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {
                    Question question = questionRepository.save(new Question(questionRequest,questionRequest.getQuestionType()));
                    question.setTest(test);
                }
                return new SimpleResponse("Successfully saved", "SAVE");
            }
        }

        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR && questionRequest.getNumberOfReplays() <= 0) {
            throw new BadRequestException("In this question should not be an empty or equal to zero field <Number of replays>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR && questionRequest.getCorrectAnswer().isEmpty()) {
            throw new BadRequestException("In this question should not be an empty field <Correct answer>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR) {
            if (questionRequest.getContentRequest().getContentType() == ContentType.AUDIO) {
                Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(), questionRequest.getStatement(), questionRequest.getNumberOfReplays(),
                        questionRequest.getCorrectAnswer(), new Content(questionRequest.getContentRequest().getContentType(), questionRequest.getContentRequest().getContent()),
                        questionRequest.getQuestionType()));
                question.setTest(test);
                return new SimpleResponse("Successfully saved", "SAVE");
            } else throw new BadRequestException("The questions content type should be <AUDIO>!");
        }

        if (questionRequest.getQuestionType() == QuestionType.DESCRIBE_IMAGE && questionRequest.getCorrectAnswer().isEmpty()) {
            throw new BadRequestException("In this question should not be an empty field <Correct answer>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.DESCRIBE_IMAGE) {
            if (questionRequest.getContentRequest().getContentType() == ContentType.IMAGE) {
                Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(), questionRequest.getCorrectAnswer(),
                        new Content(questionRequest.getContentRequest().getContentType(), questionRequest.getContentRequest().getContent()), questionRequest.getQuestionType()));
                question.setTest(test);
                return new SimpleResponse("Successfully saved", "SAVE");
            } else throw new BadRequestException("The questions content type should be <IMAGE>!");
        }

        if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT && questionRequest.getStatement().isEmpty()) {
            throw new BadRequestException("In this question should not be an empty field <Statement>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT && questionRequest.getCorrectAnswer().isEmpty()) {
            throw new BadRequestException("In this question should not be an empty field <Correct answer>!!!!");
        } else if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT) {
            Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getStatement(), questionRequest.getDuration(),
                    questionRequest.getCorrectAnswer(), questionRequest.getQuestionType()));
            question.setTest(test);
            return new SimpleResponse("Successfully saved", "SAVE");
        }

        if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS && questionRequest.getStatement().isEmpty()) {
            throw new BadRequestException("In this question, there should not be an empty field <Question statement>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS && questionRequest.getMinNumberOfWords() <= 0) {
            throw new BadRequestException("In this question, there should not be an empty or zero field <Minimum words>!!!");
        } else if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS){
            Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(), questionRequest.getQuestionType(),
                    questionRequest.getStatement(), questionRequest.getMinNumberOfWords()));
            question.setTest(test);
            return new SimpleResponse("Successfully saved", "SAVE");
        }

        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getPassage().isEmpty()) {
            throw new BadRequestException("There should be no an empty passage in this question!");
        }
        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getStatement().isEmpty()) {
            throw new BadRequestException("In this question should not be an empty field <Question statement to the Passage>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getCorrectAnswer().isEmpty()) {
            throw new BadRequestException("The field <Highlight correct answer> should not be empty!");
        } else {
            Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getStatement(),
                    questionRequest.getPassage(), questionRequest.getDuration(), questionRequest.getCorrectAnswer(), questionRequest.getQuestionType()));
            question.setTest(test);
        }
        return new SimpleResponse("Successfully saved", "SAVE");
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
