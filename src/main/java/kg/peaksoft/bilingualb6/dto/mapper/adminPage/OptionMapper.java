package kg.peaksoft.bilingualb6.dto.mapper.adminPage;

import kg.peaksoft.bilingualb6.dto.request.OptionRequest;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.entites.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptionMapper {
    public List<Option> mapToEntity(List<OptionRequest> optionRequestList) {
        List<Option> options = new ArrayList<>();
        for (OptionRequest optionRequest : optionRequestList){
            options.add(mapToEntity(optionRequest));
        }
        return options;
    }

    public Option mapToEntity(OptionRequest optionRequest){
        return Option.builder()
                .option(optionRequest.getOption())
                .isTrue(optionRequest.getIsTrue())
                .build();
    }
}
