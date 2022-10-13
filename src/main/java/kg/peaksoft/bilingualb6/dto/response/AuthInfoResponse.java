package kg.peaksoft.bilingualb6.dto.response;

import kg.peaksoft.bilingualb6.entites.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthInfoResponse {

    private String email;

    private String token;

    private Role role;
}
