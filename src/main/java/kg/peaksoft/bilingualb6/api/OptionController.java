package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/option")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Option API", description = "The option's method for delete")
public class OptionController {

    private final OptionService optionService;

    @Operation(summary = "Delete option",
            description = "Delete option by option id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id) {
        return optionService.deleteOption(id);
    }

}
