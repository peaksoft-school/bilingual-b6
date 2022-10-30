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
                .minNumberOfWords(questionRequest.getMinNumberOfWords())
                .correctAnswer(questionRequest.getCorrectAnswer())
                .content(contentMapper.mapToEntity(questionRequest.getContentRequest()))
                .optionType(questionRequest.getOptionType())
                .questionType(questionRequest.getQuestionType())
                .options(optionMapper.mapToEntity(questionRequest.getOptions()))
                .build();
    }
}
