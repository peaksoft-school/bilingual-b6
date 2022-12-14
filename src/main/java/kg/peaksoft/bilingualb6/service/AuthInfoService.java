package kg.peaksoft.bilingualb6.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import kg.peaksoft.bilingualb6.dto.request.AuthInfoRequest;
import kg.peaksoft.bilingualb6.dto.request.ClientRegisterRequest;
import kg.peaksoft.bilingualb6.dto.response.AuthInfoResponse;
import kg.peaksoft.bilingualb6.dto.response.ClientRegisterResponse;
import kg.peaksoft.bilingualb6.entites.AuthInfo;
import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.entites.enums.Role;
import kg.peaksoft.bilingualb6.exceptions.BadCredentialsException;
import kg.peaksoft.bilingualb6.exceptions.BadRequestException;
import kg.peaksoft.bilingualb6.repository.AuthInfoRepository;
import kg.peaksoft.bilingualb6.repository.ClientRepository;
import kg.peaksoft.bilingualb6.config.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthInfoService {

    private final AuthInfoRepository authInfoRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("bilingual_c.json")
                        .getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();

        FirebaseApp.initializeApp(firebaseOptions);
    }


    public AuthInfoResponse login(AuthInfoRequest authInfoRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authInfoRequest.getEmail(),
                        authInfoRequest.getPassword()));

        AuthInfo authInfo = authInfoRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadCredentialsException("Password or email not found!"));

        if (authInfoRequest.getPassword().isBlank()) {
            throw new BadRequestException("Write password!");
        }

        if (!passwordEncoder.matches(authInfoRequest.getPassword(), authInfo.getPassword())) {
            throw new BadCredentialsException("Password or email not found!");
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
                    clientRegisterRequest.getEmail() + " is not empty!");
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

    public AuthInfoResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {

        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);

        Client client;

        if (!authInfoRepository.existsAuthInfoByEmail(firebaseToken.getEmail())) {

            client = new Client();

            client.setFirstName(firebaseToken.getName());

            client.setAuthInfo(new AuthInfo(firebaseToken.getEmail(), firebaseToken.getEmail(), Role.CLIENT));

            clientRepository.save(client);
        }

        client = clientRepository.findClientByAuthInfoEmail(firebaseToken.getEmail());

        return new AuthInfoResponse(client.getAuthInfo().getEmail(),
                jwtUtils.generateToken(client.getAuthInfo().getEmail()),
                client.getAuthInfo().getRole());
    }
}