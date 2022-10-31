package kg.peaksoft.bilingualb6.entites;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Test {

    @Id
    @GeneratedValue(generator = "test_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "test_generator", sequenceName = "test_id_sequence", allocationSize = 1,initialValue = 2)
    private Long id;

    @Column(length = 10000)
    private String shortDescription;

    @Column(length = 10000)
    private String title;

    @Column
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "test")
    private List<Question> questions;
}