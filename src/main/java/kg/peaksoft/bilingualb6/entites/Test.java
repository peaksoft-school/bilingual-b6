package kg.peaksoft.bilingualb6.entites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(generator = "test_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "test_generator", sequenceName = "test_id_sequence", allocationSize = 1)
    private Long id;

    private String shortDescription;

    private String title;

    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "test")
    private List<Question> question;
}