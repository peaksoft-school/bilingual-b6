package kg.peaksoft.bilingualb6.entites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
public class Option {

    @Id
    @GeneratedValue(generator = "option_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "option_generator", sequenceName = "option_id_sequence", allocationSize = 1,initialValue = 15)
    private Long id;

    @Column(length = 10000)
    private String option;

    private Boolean isTrue;

    @ManyToOne(cascade = {MERGE, DETACH, PERSIST, REFRESH})
    private Question question;
}
