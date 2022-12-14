package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(generator = "question_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "question_generator", sequenceName = "question_id_sequence", allocationSize = 1,initialValue = 10)
    private Long id;

    @Column(length = 10000)
    private String title;

    @Column(length = 10000)
    private String statement;

    @Column(length = 10000)
    private String passage;

    @Column
    private Boolean isActive;

    @Column
    private Integer numberOfReplays;

    @Column
    private Integer duration;

    @Column
    private Integer minNumberOfWords;

    @Column(length = 10000)
    private String correctAnswer;

    @OneToOne(cascade = {PERSIST, DETACH, MERGE, REFRESH})
    @JsonIgnore
    private Content content;

    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column
    private Integer questionNumber;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Test test;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private List<Option> options;

    @OneToOne(cascade = ALL,mappedBy = "question")
    private QuestionAnswer questionAnswer;

    public Question(QuestionRequest request, Integer questionNumber) {
        this.title = request.getTitle();
        this.duration = request.getDuration();
        this.questionType = request.getQuestionType();
        this.isActive = true;
        this.content = new Content(request.getContentRequest().getContentType(),request.getContentRequest().getContent());
        this.options = new ArrayList<>();
        for (OptionRequest o : request.getOptions()){
            this.options.add(new Option(o));
        }
        this.questionNumber = questionNumber;
    }

    public Question(QuestionRequest request, QuestionType questionType, Integer questionNumber) {
        this.title = request.getTitle();
        this.duration = request.getDuration();
        this.questionType = questionType;
        this.passage = request.getPassage();
        this.isActive = true;
        this.content = new Content(request.getContentRequest().getContentType(),request.getContentRequest().getContent());
        this.options = new ArrayList<>();
        for (OptionRequest o : request.getOptions()){
            this.options.add(new Option(o));
        }
        this.questionNumber = questionNumber;
    }

    public Question(String title, Integer duration, String statement, Integer numberOfReplays, String correctAnswer, Content content, QuestionType questionType, Integer questionNumber) {
        this.title = title;
        this.duration = duration;
        this.statement = statement;
        this.isActive = true;
        this.numberOfReplays = numberOfReplays;
        this.correctAnswer = correctAnswer;
        this.content = content;
        this.questionType = questionType;
        this.questionNumber = questionNumber;
    }

    public Question(String title, Integer duration, String correctAnswer, Content content, QuestionType questionType, Integer questionNumber) {
        this.title = title;
        this.duration = duration;
        this.isActive = true;
        this.correctAnswer = correctAnswer;
        this.content = content;
        this.questionType = questionType;
        this.questionNumber = questionNumber;
    }

    public Question(String title, String statement, String passage, Integer duration, String correctAnswer, QuestionType questionType, Content content, Integer questionNumber) {
        this.title = title;
        this.isActive = true;
        this.statement = statement;
        this.passage = passage;
        this.duration = duration;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.content = content;
        this.questionNumber = questionNumber;
    }

    public Question(String title, String statement, Integer duration, String correctAnswer, QuestionType questionType, Content content, Integer questionNumber) {
        this.title = title;
        this.isActive = true;
        this.statement = statement;
        this.duration = duration;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.content = content;
        this.questionNumber = questionNumber;
    }

    public Question(String title, Integer duration, QuestionType questionType, String statement, Integer minNumberOfWords, Content content, Integer questionNumber) {
        this.title = title;
        this.isActive = true;
        this.statement = statement;
        this.duration = duration;
        this.minNumberOfWords = minNumberOfWords;
        this.questionType = questionType;
        this.content = content;
        this.questionNumber = questionNumber;
    }

    public void addOption(Option option){
        if (options==null){
            options=new ArrayList<>();
        }
        options.add(option);
    }
}