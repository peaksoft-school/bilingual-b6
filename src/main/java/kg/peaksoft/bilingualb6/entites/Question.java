package kg.peaksoft.bilingualb6.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb6.entites.enums.OptionType;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import lombok.*;

import javax.persistence.*;
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

    @Column(length = 10000)
    private String shortDescription;

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

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Test test;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER, mappedBy = "question")
    private List<Option> options;

    @OneToOne(cascade = ALL,mappedBy = "question")
    private QuestionAnswer questionAnswer;

}