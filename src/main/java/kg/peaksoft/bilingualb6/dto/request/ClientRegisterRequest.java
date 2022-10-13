package kg.peaksoft.bilingualb6.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientRegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
