package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.response.ResultResponses;
import kg.peaksoft.bilingualb6.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/result")
@Tag(name = "Result API", description = "The result's GET methods")
public class ResultController {

    private final ResultService resultService;

    @Operation(summary = "Client results",
            description = "Authenticate client test results")
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping()
    public List<ResultResponses> getAll() {
        return resultService.getAll();
    }
}