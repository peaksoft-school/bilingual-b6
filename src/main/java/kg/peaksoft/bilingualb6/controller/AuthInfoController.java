package kg.peaksoft.bilingualb6.controller;

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
@Tag(name = "authInfoController",description = "AuthApi(for authentication)")
public class AuthInfoController {

    private final AuthInfoService authInfoService;

    @Operation(summary = "email login",description = "authentication with registered account")
    @PostMapping("/login")
    @PermitAll
    public AuthInfoResponse login(@RequestBody AuthInfoRequest authInfoRequest) {
        return authInfoService.login(authInfoRequest);
    }

    @Operation(summary = "register", description = "registration new account")
    @PostMapping("/register")
    @PermitAll
    public ClientRegisterResponse register(@RequestBody ClientRegisterRequest clientRegisterRequest) {
        return authInfoService.register(clientRegisterRequest);
    }

    @Operation(summary = "authentication",description = "authentication with google account")
    @PostMapping("/authenticate/google")
    @PermitAll
    public AuthInfoResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {
        return authInfoService.authWithGoogleAccount(tokenId);
    }

}
