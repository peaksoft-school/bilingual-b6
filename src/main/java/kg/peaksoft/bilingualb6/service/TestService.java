package kg.peaksoft.bilingualb6.service;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@RequiredArgsConstructor
@Service
@Transactional
public class TestService {
    private final TestRepository testRepository;

    public SimpleResponse enable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Test with =%s id not found", id)));
        String a;
        if (test.getIsActive()) {
            a = "enabled";
        } else {
            test.setIsActive(true);
            a = "enabled";
        }
        return new SimpleResponse(String.format("Test with =%s id is = %s", id, a));
    }

    public SimpleResponse disable(Long id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Test with =%s id not " +
                        "found", id)));
        String a;
        if (!test.getIsActive()) {
            a = "disabled";
        } else {
            test.setIsActive(false);
            a = "disabled";
        }
        return new SimpleResponse(String.format("Test with = %s id is = %s", id, a));
    }
}
