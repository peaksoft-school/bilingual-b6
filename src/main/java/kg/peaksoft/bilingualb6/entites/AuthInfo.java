package kg.peaksoft.bilingualb6.entites;

import kg.peaksoft.bilingualb6.entites.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "auth_info")
@Getter
@Setter
@NoArgsConstructor
public class AuthInfo{

    @Id
    @GeneratedValue(generator = "auth_info_generator",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "auth_info_generator", sequenceName = "auth_info_id_sequence", allocationSize = 1)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
