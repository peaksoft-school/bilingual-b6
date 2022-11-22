package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {

    @Id
    @GeneratedValue(generator = "option_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "option_generator", sequenceName = "option_id_sequence", allocationSize = 1,initialValue = 15)
    private Long id;

    @Column(length = 10000)
    private String option;

    private Boolean isTrue;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH})
    @JoinTable(name = "questions_options",
            joinColumns = @JoinColumn(name = "options_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    @JsonIgnore
    private Question question;

    @ManyToOne(cascade = {REFRESH,MERGE,DETACH})
    @JoinTable(name = "question_answers_options",joinColumns = @JoinColumn(name = "options_id"),
    inverseJoinColumns = @JoinColumn(name = "question_answer_id"))
    @JsonIgnore
    private QuestionAnswer questionAnswer;

    public Option (OptionRequest request) {
        this.option = request.getOption();
        this.isTrue = request.getIsTrue();
    }
}
