package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb6.entites.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    private Integer numberOfWords;//user's answer's counter words

    @Column
    private Integer score;

    @Column
    private Boolean seen;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = {REFRESH,MERGE,DETACH,PERSIST})
    @JsonIgnore
    private Content content;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Question question;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH},mappedBy = "questionAnswer")
    List<Option> options;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REMOVE})
    private Result result;


}
