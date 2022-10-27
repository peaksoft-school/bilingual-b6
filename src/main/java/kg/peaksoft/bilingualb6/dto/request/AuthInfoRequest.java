package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.validation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class AuthInfoRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @PasswordValid
    private String password;
}