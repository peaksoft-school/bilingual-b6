package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "question_answers")
@Getter
@Setter
@NoArgsConstructor
public class QuestionAnswer {

    @Id
    @GeneratedValue(generator = "clients_answer_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "clients_answer_generator", sequenceName = "clients_answer_id_sequence", allocationSize = 1, initialValue = 10)
    private Long id;

    @Column
    private Integer numberOfWords;

    @Column
    private String textResponseUser;

    @Column
    private Float score;

    @OneToOne(cascade = {REFRESH, MERGE, DETACH, PERSIST})
    @JsonIgnore
    private Content content;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Question question;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH}, mappedBy = "questionAnswer")
//            @JoinTable(name = "question_answers_options",
//                    foreignKey =@ForeignKey(name = "question_answer_id"),
//            inverseForeignKey = @ForeignKey(name = "options_id"))
//        @JoinTable(name = "question_answers_options",joinColumns = @JoinColumn(name = "question_answer_id"),
//    inverseJoinColumns = @JoinColumn(name = "options_id"))
    Set<Option> options;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REMOVE})
    private Result result;

    public QuestionAnswer(Float score, Question question, Set<Option> options, Result result) {
        this.score = score;
        this.question = question;
        this.options = new HashSet<>();
        this.options.addAll(options);
        this.result = result;
    }

    public QuestionAnswer(String textResponseUser, Float score, Question question, Result result) {
        this.textResponseUser = textResponseUser;
        this.score = score;
        this.question = question;
        this.result = result;
    }

    public QuestionAnswer(String textResponseUser, Float score, Content content, Question question, Result result) {
        this.textResponseUser = textResponseUser;
        this.score = score;
        this.content = content;
        this.question = question;
        this.result = result;
    }

    public QuestionAnswer(Integer numberOfWords, String textResponseUser, Float score, Question question, Result result) {
        this.numberOfWords = numberOfWords;
        this.textResponseUser = textResponseUser;
        this.score = score;
        this.question = question;
        this.result = result;
    }

    public void addOption(Option option) {
        this.options.add(option);
        option.setQuestionAnswer(this);
    }
}
