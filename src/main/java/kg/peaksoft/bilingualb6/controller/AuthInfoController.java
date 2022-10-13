package kg.peaksoft.bilingualb6.controller;

import kg.peaksoft.bilingualb6.dto.request.AuthInfoRequest;
import kg.peaksoft.bilingualb6.dto.request.ClientRegisterRequest;
import kg.peaksoft.bilingualb6.dto.response.AuthInfoResponse;
import kg.peaksoft.bilingualb6.dto.response.ClientRegisterResponse;
import kg.peaksoft.bilingualb6.service.AuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authInfo")
public class AuthInfoController {

    private final AuthInfoService authInfoService;

    @PostMapping("/login")
    public AuthInfoResponse login(@RequestBody AuthInfoRequest authInfoRequest) {
        return authInfoService.login(authInfoRequest);
    }

    @PostMapping("/register")
    public ClientRegisterResponse register(@RequestBody ClientRegisterRequest clientRegisterRequest) {
        return authInfoService.register(clientRegisterRequest);
    }

}
