package kg.peaksoft.bilingualb6.dto.mapper.adminPage;

import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.entites.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final OptionMapper optionMapper;

    private final ContentMapper contentMapper;

    public Question mapToEntity(QuestionRequest questionRequest) {
        return Question.builder()
                .title(questionRequest.getTitle())
                .statement(questionRequest.getStatement())
                .passage(questionRequest.getPassage())
                .isActive(questionRequest.getIsActive())
                .numberOfReplays(questionRequest.getNumberOfReplays())
                .duration(questionRequest.getDuration())
                .shortDescription(questionRequest.getShortDescription())
                .minNumberOfWords(questionRequest.getMinNumberOfWords())
                .correctAnswer(questionRequest.getCorrectAnswer())
                .content(contentMapper.mapToEntity(questionRequest.getContentRequest()))
                .optionType(questionRequest.getOptionType())
                .questionType(questionRequest.getQuestionType())
                .options(optionMapper.mapToEntity(questionRequest.getOptions()))
                .build();
//        Question question = new Question();
//        question.setTitle(questionRequest.getTitle());
//        question.setStatement(questionRequest.getStatement());
//        question.setPassage(questionRequest.getPassage());
//        question.setIsActive(questionRequest.getIsActive());
//        question.setNumberOfReplays(questionRequest.getNumberOfReplays());
//        question.setDuration(questionRequest.getDuration());
//        question.setShortDescription(questionRequest.getShortDescription());
//        question.setMinNumberOfWords(questionRequest.getMinNumberOfWords());
//        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
//        question.setContent(contentMapper.mapToEntity(questionRequest.getContentRequest()));
//        question.setOptionType(questionRequest.getOptionType());
//        question.setQuestionType(questionRequest.getQuestionType());
//        question.setOptions(optionMapper.mapToEntity(questionRequest.getOptions()));
//        Option option = new Option();
//        option.setQuestion(question);
//        return question;
    }

//    public QuestionResponse mapToResponse(Question question) {
//        return QuestionResponse.builder()
//                .id(question.getId())
//                .title(question.getTitle())
//                .duration(question.getDuration())
//                .build();
//    }
}
