package kg.peaksoft.bilingualb6.api;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.request.AuthInfoRequest;
import kg.peaksoft.bilingualb6.dto.request.ClientRegisterRequest;
import kg.peaksoft.bilingualb6.dto.response.AuthInfoResponse;
import kg.peaksoft.bilingualb6.dto.response.ClientRegisterResponse;
import kg.peaksoft.bilingualb6.service.AuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Authentication API",description = "The authentication API (for authentication)")
public class AuthInfoController {

    private final AuthInfoService authInfoService;

    @Operation(summary = "Retrieve Authentication Token",
            description = "This endpoint returns a JWT for authenticating further requests to the API")
    @PostMapping("/login")
    public AuthInfoResponse login(@RequestBody AuthInfoRequest authInfoRequest) {
        return authInfoService.login(authInfoRequest);
    }

    @Operation(summary = "Registration", description = "The endpoint for register user")
    @PostMapping("/register")
    public ClientRegisterResponse register(@RequestBody ClientRegisterRequest clientRegisterRequest) {
        return authInfoService.register(clientRegisterRequest);
    }

    @Operation(summary = "Authentication with google",description = "Authentication via Google using Firebase")
    @PostMapping("/authenticate/google")
    public AuthInfoResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {
        return authInfoService.authWithGoogleAccount(tokenId);
    }

}
