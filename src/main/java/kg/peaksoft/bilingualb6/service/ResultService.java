package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.ResultResponses;
import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.repository.ClientRepository;
import kg.peaksoft.bilingualb6.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {

    private final ResultRepository resultRepository;
    private final ClientRepository clientRepository;

    public List<ResultResponses> getAll(String email) {
        Client client = clientRepository.getClientByAuthInfoEmail(email);
        return resultRepository.findAll(client.getId());
    }
}