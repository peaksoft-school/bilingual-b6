package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.OptionResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Content;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.entites.enums.ContentType;
import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;

    private final OptionRepository optionRepository;

    public SimpleResponse save(QuestionRequest questionRequest) {
        Test test = testRepository.findById(questionRequest.getTestId()).orElseThrow(
                () -> {
                    log.error("Test not found!");
                    throw new NotFoundException(String.format("Test not found"));
                });
        if (questionRequest.getDuration().equals(0) || questionRequest.getDuration() == null) {
            log.error("The duration should not equal to zero or not be an empty!");
            throw new BadRequestException("The duration should not equal to zero or not be an empty!");
        }

        if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getOptions().isEmpty() & questionRequest.getOptions() == null) {
            log.error("Add at least one option!");
            throw new BadRequestException("Add at least one option!");
        }
        if (questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getPassage().isEmpty() ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getPassage().isEmpty()) {
            log.error("There should be no empty passage in this question!");
            throw new BadRequestException("There should be no empty passage in this question!");
        }
        if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
                questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA ||
                questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE) {
            if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS && questionRequest.getContentRequest().getContentType() != ContentType.TEXT ||
                    questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA && questionRequest.getContentRequest().getContentType() != ContentType.TEXT ||
                    questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE && questionRequest.getContentRequest().getContentType() != ContentType.TEXT) {
                log.error("The question option type should be the <TEXT> format!");
                throw new BadRequestException("The question option type should be the <TEXT> format!");
            }
            if (questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD && questionRequest.getContentRequest().getContentType() != ContentType.AUDIO) {
                log.error("The question option type should be the <AUDIO> format!");
                throw new BadRequestException("The question option type should be the <AUDIO> format!");
            } else if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                    questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
                int numberOfTrueOptions = 0;
                for (OptionRequest optionRequest : questionRequest.getOptions()) {
                    if (optionRequest.getIsTrue()) {
                        numberOfTrueOptions++;
                    }
                    if (optionRequest.getOption().isEmpty() || optionRequest.getOption() == null) {
                        log.error("The option should not be empty!");
                        throw new BadRequestException("The option should not be empty!");
                    }
                }
                if (numberOfTrueOptions >= 1) {
                    if (questionRequest.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS) {
                        Question question = questionRepository.save(new Question(questionRequest, 1));
                        question.setOptionType(OptionType.MULTIPLE_CHOICE);
                        question.setTest(test);
                    }
                    if (questionRequest.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
                        Question question = questionRepository.save(new Question(questionRequest, 2));
                        question.setOptionType(OptionType.MULTIPLE_CHOICE);
                        question.setTest(test);
                    }
                    log.info("Successfully saved!");
                    return new SimpleResponse("Successfully saved", "SAVE");
                } else throw new BadRequestException("Add at least two or more correct options!");

            } else if (questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE ||
                    questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {
                int numberOfTrueOption = 0;
                for (OptionRequest optionRequest : questionRequest.getOptions()) {
                    if (optionRequest.getIsTrue()) {
                        numberOfTrueOption++;
                    }
                    if (optionRequest.getOption().isEmpty() || optionRequest.getOption() == null) {
                        log.error("The option should not be empty!");
                        throw new BadRequestException("The option should not be empty!");
                    }
                }
                if (numberOfTrueOption == 1) {
                    if (questionRequest.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {
                        Question question = questionRepository.save(new Question(questionRequest, questionRequest.getQuestionType(), 8));
                        question.setOptionType(OptionType.SINGLE_CHOICE);
                        question.setTest(test);
                    }
                    if (questionRequest.getQuestionType() == QuestionType.SELECT_BEST_TITLE) {
                        Question question = questionRepository.save(new Question(questionRequest, questionRequest.getQuestionType(), 9));
                        question.setOptionType(OptionType.SINGLE_CHOICE);
                        question.setTest(test);
                    }
                    return new SimpleResponse("Successfully saved", "SAVE");
                } else throw new BadRequestException("The count of correct option of the question should be one!");
            }
        }

        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR && questionRequest.getNumberOfReplays() <= 0) {
            log.error("In this question should not be an empty or equal to zero field <Number of replays>!");
            throw new BadRequestException("In this question should not be an empty or equal to zero field <Number of replays>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR && questionRequest.getCorrectAnswer().isEmpty()) {
            log.error("In this question should not be an empty field <Correct answer>!");
            throw new BadRequestException("In this question should not be an empty field <Correct answer>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR) {
            if (questionRequest.getContentRequest().getContentType() == ContentType.AUDIO) {
                Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(),
                        questionRequest.getStatement(), questionRequest.getNumberOfReplays(), questionRequest.getCorrectAnswer(),
                        new Content(questionRequest.getContentRequest().getContentType(), questionRequest.getContentRequest().getContent()),
                        questionRequest.getQuestionType(),3));
                question.setTest(test);
                log.info("Successfully saved!");
                return new SimpleResponse("Successfully saved", "SAVE");
            } else throw new BadRequestException("The questions content type should be <AUDIO>!");
        }

        if (questionRequest.getQuestionType() == QuestionType.DESCRIBE_IMAGE && questionRequest.getCorrectAnswer().isEmpty()) {
            log.error("In this question should not be an empty field <Correct answer>!");
            throw new BadRequestException("In this question should not be an empty field <Correct answer>!");
        }
        if (questionRequest.getQuestionType() == QuestionType.DESCRIBE_IMAGE) {
            if (questionRequest.getContentRequest().getContentType() == ContentType.IMAGE) {
                Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(), questionRequest.getCorrectAnswer(),
                        new Content(questionRequest.getContentRequest().getContentType(), questionRequest.getContentRequest().getContent()), questionRequest.getQuestionType(), 4));
                question.setTest(test);
                return new SimpleResponse("Successfully saved", "SAVE");
            } else throw new BadRequestException("The questions content type should be <IMAGE>!");
        }

        if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT && questionRequest.getStatement().isEmpty()) {
            log.error("In this question should not be an empty field <Statement>!!!");
            throw new BadRequestException("In this question should not be an empty field <Statement>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT && questionRequest.getCorrectAnswer().isEmpty()) {
            log.error("In this question should not be an empty field <Correct answer>!!!!");
            throw new BadRequestException("In this question should not be an empty field <Correct answer>!!!!");
        } else if (questionRequest.getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT) {
            Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getStatement(), questionRequest.getDuration(),
                    questionRequest.getCorrectAnswer(), questionRequest.getQuestionType(), new Content(ContentType.TEXT, "text"), 5));
            question.setTest(test);
            log.info("Successfully saved");
            return new SimpleResponse("Successfully saved", "SAVE");
        }

        if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS && questionRequest.getStatement().isEmpty()) {
            log.error("In this question, there should not be an empty field <Question statement>!!!");
            throw new BadRequestException("In this question, there should not be an empty field <Question statement>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS && questionRequest.getMinNumberOfWords() <= 0) {
            log.error("In this question, there should not be an empty or zero field <Minimum words>!!!");
            throw new BadRequestException("In this question, there should not be an empty or zero field <Minimum words>!!!");
        } else if (questionRequest.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS) {
            Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getDuration(), questionRequest.getQuestionType(),
                    questionRequest.getStatement(), questionRequest.getMinNumberOfWords(), new Content(ContentType.TEXT, "text"), 6));
            question.setTest(test);
            log.info("Successfully saved");
            return new SimpleResponse("Successfully saved", "SAVE");
        }

        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getPassage().isEmpty()) {
            log.error("There should be no an empty passage in this question!");
            throw new BadRequestException("There should be no an empty passage in this question!");
        }
        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getStatement().isEmpty()) {
            log.error("In this question should not be an empty field <Question statement to the Passage>!!!");
            throw new BadRequestException("In this question should not be an empty field <Question statement to the Passage>!!!");
        }
        if (questionRequest.getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER && questionRequest.getCorrectAnswer().isEmpty()) {
            log.error("The field <Highlight correct answer> should not be empty!");
            throw new BadRequestException("The field <Highlight correct answer> should not be empty!");
        } else {
            Question question = questionRepository.save(new Question(questionRequest.getTitle(), questionRequest.getStatement(),
                    questionRequest.getPassage(), questionRequest.getDuration(), questionRequest.getCorrectAnswer(), questionRequest.getQuestionType(), new Content(ContentType.TEXT, "text"), 7));
            question.setTest(test);
        }
        log.info("Successfully saved!");
        return new SimpleResponse("Successfully saved", "SAVE");
    }

    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Question not found!");
                    throw new NotFoundException("Question not found!");
                });

        List<OptionResponse> optionsList = optionRepository.getAllOptionsByQuestionId(id);
        QuestionResponse response = new QuestionResponse();

        response.setId(question.getId());
        response.setTitle(question.getTitle());
        response.setPassage(question.getPassage());
        response.setIsActive(question.getIsActive());
        response.setCorrectAnswer(question.getCorrectAnswer());
        response.setNumberOfReplays(question.getNumberOfReplays());
        response.setMinNumberOfWords(question.getMinNumberOfWords());
        response.setDuration(question.getDuration());
        if (question.getContent() == null) {
            response.setContent(null);
        } else {
            response.setContent(question.getContent().getContent());
        }
        response.setShortDescription(question.getTest().getShortDescription());
        response.setQuestionType(question.getQuestionType());
        response.setStatement(question.getStatement());
        response.setOptionResponseList(optionsList);
        return response;
    }

    public SimpleResponse enableDisable(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Question not found!");
                    throw new NotFoundException("Question not found!");
                });
        question.setIsActive(!question.getIsActive());
        String a;
        if (question.getIsActive()) {
            a = "enabled";
            log.info("Question with id: " + id + "enabled");
        } else {
            a = "disabled";
            log.info("Question with id: " + id + "disabled");
        }
        log.info("Question with id:" + id + "successfully is %s", a);
        return new SimpleResponse(String.format("Question successfully is %s", a), "ok");
    }

    public SimpleResponse delete(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Question not found!");
                    throw new NotFoundException("Question not found!");
                });
        if (question != null) {
            questionRepository.updateByIdForDeleteQuestionToContentId(id);
            questionRepository.updateByIdForDeleteQuestionToTestId(id);
            questionRepository.delete(question);
        }
        log.info("Question with id: "+ id + "deleted");
        return new SimpleResponse("deleted", "ok");
    }

    public SimpleResponse update(Long id, QuestionUpdateRequest questionUpdateRequest) {
        Question question = questionRepository.findById(id).orElseThrow(() -> {
            log.error("Question with id: " + id + "not found!");
           throw new NotFoundException("Question not found!");
        });

        List<OptionResponse> optionsList = optionRepository.getAllOptionsByQuestionId(id);

        for (OptionRequest q : questionUpdateRequest.getOptionRequests()) {
            Option option = new Option(q);
            question.addOption(option);
        }

        for (OptionResponse option : optionsList) {
            Long optionId = option.getId();

            for (Long requestId : questionUpdateRequest.getWillDelete()) {
                if (requestId.equals(optionId)) {
                    optionRepository.deleteById(requestId);
                }
            }

            for (Long requestId : questionUpdateRequest.getWillUpdate()) {
                if (question.getQuestionType() == QuestionType.SELECT_BEST_TITLE || question.getQuestionType() == QuestionType.SELECT_MAIN_IDEA) {
                    for (Option o : question.getOptions()) {
                        if (o.getIsTrue()) {
                            o.setIsTrue(false);
                        }
                    }
                    Option option1 = optionRepository.findById(requestId).
                            orElseThrow(() -> {
                                log.error("Option not found!");
                                throw new NotFoundException("Option not found!");
                            });
                    option1.setIsTrue(true);
                }

                if (requestId.equals(optionId)) {
                    Option option1 = optionRepository.findById(requestId).
                            orElseThrow(() -> {
                                log.error("Option not found!");
                                throw new NotFoundException("Option not found!");
                            });
                    option1.setIsTrue(!option1.getIsTrue());
                }
            }
        }

        question.setTitle(questionUpdateRequest.getTitle());
        question.setStatement(questionUpdateRequest.getStatement());
        question.setPassage(questionUpdateRequest.getPassage());
        question.setNumberOfReplays(questionUpdateRequest.getNumberOfReplays());
        question.setDuration(questionUpdateRequest.getDuration());
        question.setCorrectAnswer(questionUpdateRequest.getCorrectAnswer());
        question.setMinNumberOfWords(questionUpdateRequest.getMinNumberOfWords());
        if (question.getContent() == null) {
            question.setContent(null);
        } else {
            question.getContent().setContent(questionUpdateRequest.getContent());
        }
        log.info("Question is successfully updated!");
        return new SimpleResponse("Question is successfully updated!", "ok");
    }
}
