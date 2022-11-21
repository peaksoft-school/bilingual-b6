package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.OptionResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionUpdateResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Content;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.ContentRepository;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;

    private final OptionRepository optionRepository;

    private final ContentRepository contentRepository;

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
            } else if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                    questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
                int numberOfTrueOptions = 0;
                for (OptionRequest optionRequest : questionRequest.getOptions()) {
                    if (optionRequest.getIsTrue()) {
                        numberOfTrueOptions++;
                    }
                    if (optionRequest.getOption().isEmpty() || optionRequest.getOption() == null){
                        throw new BadRequestException("The option should not be empty!");
                    }
                }
                if (numberOfTrueOptions >= 1) {
                    Question question = questionRepository.save(new Question(questionRequest));
                    question.setOptionType(OptionType.MULTIPLE_CHOICE);
                    question.setTest(test);
                    return new SimpleResponse("Successfully saved", "SAVE");
                } else throw new BadRequestException("Add at least two or more correct options!");

            } else if (questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE ||
                    questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {
                int numberOfTrueOption = 0;
                for (OptionRequest optionRequest : questionRequest.getOptions()) {
                    if (optionRequest.getIsTrue()) {
                        numberOfTrueOption++;
                    }
                    if (optionRequest.getOption().isEmpty() || optionRequest.getOption() == null){
                        throw new BadRequestException("The option should not be empty!");
                    }
                }
                if (numberOfTrueOption == 1) {
                    Question question = questionRepository.save(new Question(questionRequest, questionRequest.getQuestionType()));
                    question.setOptionType(OptionType.SINGLE_CHOICE);
                    question.setTest(test);
                    return new SimpleResponse("Successfully saved", "SAVE");
                } else throw new BadRequestException("The count of correct option of the question should be one!");
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
                Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(),
                        questionRequest.getStatement(), questionRequest.getNumberOfReplays(), questionRequest.getCorrectAnswer(),
                        new Content(questionRequest.getContentRequest().getContentType(), questionRequest.getContentRequest().getContent()),
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
        } else if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS) {
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

    public QuestionResponse getQuestionById(Long id){
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));
        questionRepository.findById(id);
        List<OptionResponse> optionsList = optionRepository.getAllOptionsByQuestionId(id);

        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .passage(question.getPassage())
                .isActive(question.getIsActive())
                .correctAnswer(question.getCorrectAnswer())
                .numberOfReplays(question.getNumberOfReplays())
                .duration(question.getDuration())
                .shortDescription(question.getTest().getShortDescription())
                .questionType(question.getQuestionType())
                .statement(question.getStatement())
                .optionResponseList(optionsList).build();
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

    public QuestionUpdateResponse update(Long id, QuestionUpdateRequest questionUpdateRequest) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Question with id = %s not found", id)));

        Content content = contentRepository.findById(questionUpdateRequest.getContent().getId()).orElseThrow(
                () -> new NotFoundException(String.format("Content with id = %s not found",
                        questionUpdateRequest.getContent().getId())));
        content.setContent(questionUpdateRequest.getContent().getContent());

        question.setTitle(questionUpdateRequest.getTitle());
        question.setStatement(questionUpdateRequest.getStatement());
        question.setPassage(questionUpdateRequest.getPassage());
        question.setNumberOfReplays(questionUpdateRequest.getNumberOfReplays());
        question.setDuration(questionUpdateRequest.getDuration());
        question.setCorrectAnswer(questionUpdateRequest.getCorrectAnswer());
        question.setMinNumberOfWords(questionUpdateRequest.getMinNumberOfWords());
        question.setContent(content);

        return QuestionUpdateResponse.builder()
                .title(question.getTitle())
                .statement(question.getStatement())
                .passage(question.getPassage())
                .numberOfReplays(question.getNumberOfReplays())
                .duration(question.getDuration())
                .correctAnswer(question.getCorrectAnswer())
                .minNumberOfWords(questionUpdateRequest.getMinNumberOfWords())
                .content(question.getContent().getContent())
                .build();
    }
}
