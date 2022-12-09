package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OptionService {

    private final OptionRepository optionRepository;

    public SimpleResponse deleteOption(Long id) {
        Option option = optionRepository.findById(id).orElseThrow(
                ()->new NotFoundException(String.format("Option not found!")));
        if (option != null) {
            optionRepository.updateByIdForDeleteInQuestion(id);
            optionRepository.deleteById(id);
        }
        return new SimpleResponse("Option is successfully deleted!","DELETE");
    }
}
