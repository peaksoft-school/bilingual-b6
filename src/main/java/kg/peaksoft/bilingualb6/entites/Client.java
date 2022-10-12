package kg.peaksoft.bilingualb6.entites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class  Client {

    @Id
    @GeneratedValue(generator = "client_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "client_generator", sequenceName = "client_id_sequence", allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private AuthInfo authInfo;

}