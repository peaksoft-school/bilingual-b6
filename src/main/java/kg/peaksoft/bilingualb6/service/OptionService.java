package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.OptionResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public SimpleResponse deleteOption(Long id) {
        Option option = optionRepository.findById(id).orElseThrow(
                ()->new NotFoundException(String.format("Option with =%s id not " +
                        "found", id)));
        if (option != null) {
            optionRepository.updateByIdForDeleteInQuestion(id);
            optionRepository.deleteById(id);
        }
        return new SimpleResponse("option with id: " + id + " is deleted","DELETE");
    }
}
