package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb6.entites.enums.Status;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Integer numberOfPlays;

    @Column
    private Boolean seen;

    @OneToOne(cascade = {REFRESH, MERGE, DETACH, PERSIST})
    @JsonIgnore
    private Content content;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Question question;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
        @JoinTable(name = "question_answers_options",
                joinColumns = @JoinColumn(name = "question_answer_id"),
                inverseJoinColumns = @JoinColumn(name = "options_id"))
    Set<Option> options;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE})
    private Result result;

    public QuestionAnswer(Float score, Question question, Set<Option> options, Result result, Boolean seen, Status status) {
        this.score = score;
        this.question = question;
        this.options = new HashSet<>();
        this.options.addAll(options);
        this.result = result;
        this.seen = seen;
        this.status = status;
    }

    public QuestionAnswer(String textResponseUser, Float score, Question question, Result result, Boolean seen, Status status) {
        this.textResponseUser = textResponseUser;
        this.score = score;
        this.question = question;
        this.result = result;
        this.seen = seen;
        this.status = status;
    }

    public QuestionAnswer(String textResponseUser, Float score, Integer numberOfPlays, Content content, Question question, Result result, Boolean seen, Status status) {
        this.textResponseUser = textResponseUser;
        this.score = score;
        this.numberOfPlays = numberOfPlays;
        this.content = content;
        this.question = question;
        this.result = result;
        this.seen = seen;
        this.status = status;
    }

    public QuestionAnswer(Integer numberOfWords, String textResponseUser, Float score, Question question, Result result, Boolean seen, Status status) {
        this.numberOfWords = numberOfWords;
        this.textResponseUser = textResponseUser;
        this.score = score;
        this.question = question;
        this.result = result;
        this.seen = seen;
        this.status = status;
    }

}
