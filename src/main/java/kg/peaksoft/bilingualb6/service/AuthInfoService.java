package kg.peaksoft.bilingualb6.service;


import kg.peaksoft.bilingualb6.dto.request.AuthInfoRequest;
import kg.peaksoft.bilingualb6.dto.request.ClientRegisterRequest;
import kg.peaksoft.bilingualb6.dto.response.AuthInfoResponse;
import kg.peaksoft.bilingualb6.dto.response.ClientRegisterResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.AuthInfo;
import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.exceptions.BadCredentialsException;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.repository.AuthInfoRepository;
import kg.peaksoft.bilingualb6.repository.ClientRepository;
import kg.peaksoft.bilingualb6.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthInfoService {


    private final AuthInfoRepository authInfoRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;
    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;


    public AuthInfoResponse login(AuthInfoRequest authInfoRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authInfoRequest.getEmail(),
                        authInfoRequest.getPassword()));

        AuthInfo authInfo = authInfoRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadCredentialsException("bad credentials"));

        if (authInfoRequest.getPassword().isBlank()) {
            throw new BadRequestException("password cannot be empty");
        }

        if (!passwordEncoder.matches(authInfoRequest.getPassword(), authInfo.getPassword())) {
            throw new BadCredentialsException("incorrect password");
        }

        String token = jwtUtils.generateToken(authInfo.getEmail());
        return new AuthInfoResponse(authInfo.getUsername(), token, authInfo.getRole());
    }

    public ClientRegisterResponse register(ClientRegisterRequest clientRegisterRequest) {

    if (clientRegisterRequest.getPassword().isBlank()) {
        throw new BadRequestException("Password cannot be empty!");
    }

    if (authInfoRepository.existsAuthInfoByEmail(clientRegisterRequest.getEmail())) {
        throw new BadRequestException("This email: " +
                clientRegisterRequest.getEmail() + " is already in use!");
    }

        clientRegisterRequest.setPassword(passwordEncoder.encode(clientRegisterRequest.getPassword()));

        Client client = new Client(clientRegisterRequest);

        Client saveClient = clientRepository.save(client);

        String token = jwtUtils.generateToken(saveClient.getAuthInfo().getEmail());

        return new ClientRegisterResponse(
                saveClient.getFirstName(),
                saveClient.getLastName(),
                saveClient.getAuthInfo().getEmail(),
                token,
                saveClient.getAuthInfo().getRole());
    }
}