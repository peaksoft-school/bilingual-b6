package kg.peaksoft.bilingualb6.dto.request;

import kg.peaksoft.bilingualb6.validation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@Valid
public class ClientRegisterRequest {

    @NotNull(message = "First name should be not null")
    @Size(min = 2,max = 30)
    private String firstName;

    @Size(min = 2,max = 30)
    @NotNull(message = "Last name should be not null")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotBlank
    @PasswordValid
    private String password;
}