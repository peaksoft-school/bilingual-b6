package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponse;
import kg.peaksoft.bilingualb6.entites.Test;
import kg.peaksoft.bilingualb6.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Test API",description = "The test CRUD methods (for manipulation)")
public class TestController {

    private final TestService testService;
    @Operation(summary = "Test status",
            description = "This endpoint returns test status to enable and disable for test further requests to the API")
    @PutMapping("/enable-disable/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse enableDisable(@PathVariable Long id){
        return testService.enableDisable(id);
    }

    public Test getTestById(@PathVariable Long id){
        return testService.getTestById(id);
    }
}
