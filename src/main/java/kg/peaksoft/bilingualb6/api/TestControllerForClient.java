package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.request.PassTestRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponseForClient;
import kg.peaksoft.bilingualb6.service.PassTestService;
import kg.peaksoft.bilingualb6.dto.response.TestResponseGetTestByIdForClient;
import kg.peaksoft.bilingualb6.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Test API for client", description = "The test methods for clients")
public class TestControllerForClient {

    private final TestService testService;

    private final PassTestService passTestService;

    @Operation(summary = "Get all test", description = "Get all test for clients")
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<TestResponseForClient> getAllTestForClient() {
        return testService.getAllTestForClient();
    }

    @Operation(summary = "Get test by id for client",
            description = "This endpoint returns test by id and their questions and options for CLIENT")
    @GetMapping("/getQuestions/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public TestResponseGetTestByIdForClient getByIdForClient(@PathVariable Long id) {
        return testService.getTestByIdForClient(id);
    }

    @Operation(summary = "Pass test", description = "Pass test for client")
    @PostMapping("/pass-test")
    @PreAuthorize("hasAuthority('CLIENT')")
    public SimpleResponse passTest(@RequestBody PassTestRequest request, Principal principal) {
        return passTestService.passTest(request,principal);
    }
}