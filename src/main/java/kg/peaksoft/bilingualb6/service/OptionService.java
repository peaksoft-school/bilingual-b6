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
                ()->{
                    log.error("Option with id: " + id + "not found!");
                   throw new NotFoundException(("Option with id: " + id + "not found!"));
                });
        if (option != null) {
            optionRepository.updateByIdForDeleteInQuestion(id);
            optionRepository.deleteById(id);
            log.info("Option with id: {} " + id + "is successfully deleted!", "DELETE");
        }
        return new SimpleResponse("Option with id: " + id + "is successfully deleted!","DELETE");
    }
}