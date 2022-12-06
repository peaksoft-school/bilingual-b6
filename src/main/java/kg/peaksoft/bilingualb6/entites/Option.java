package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import lombok.*;

import javax.persistence.*;


import java.util.List;

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

    @Column(length = 10000)
    private String title;

    private Boolean isTrue;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH})
    @JoinTable(name = "questions_options",
            joinColumns = @JoinColumn(name = "options_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    @JsonIgnore
    private Question question;


    @ManyToMany(cascade = {REFRESH,MERGE,DETACH}, mappedBy = "options")
    @JsonIgnore
    private List<QuestionAnswer> questionAnswer;

    public Option (OptionRequest request) {
        this.option = request.getOption();
        this.title = request.getTitle();
        this.isTrue = request.getIsTrue();
    }
}
