package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.request.TestRequest;
import kg.peaksoft.bilingualb6.dto.response.TestResponseForClient;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.dto.response.TestInnerPageResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponse;
import kg.peaksoft.bilingualb6.dto.response.TestResponseGetTestByIdForClient;
import kg.peaksoft.bilingualb6.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Test API", description = "The test CRUD methods (for manipulation)")
public class TestController {

    private final TestService testService;

    @Operation(summary = "Test status",
            description = "This endpoint returns test status to enable and disable for test further requests to the API")
    @PutMapping("/enable-disable/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse enableDisable(@PathVariable Long id) {
        return testService.enableDisable(id);
    }

    @Operation(summary = "Get test by id",
            description = "This endpoint returns test by id and their questions and options for ADMIN")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TestInnerPageResponse getById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

    @Operation(summary = "Get test by id for client",
            description = "This endpoint returns test by id and their questions and options for CLIENT")
    @GetMapping("/client{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public TestResponseGetTestByIdForClient getByIdForClient(@PathVariable Long id) {
        return testService.getTestByIdForClient(id);
    }

    @Operation(summary = "Create test", description = "Create new test by ADMIN")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public TestResponse save(@RequestBody TestRequest request) {
        return testService.save(request);
    }

    @Operation(summary = "Get all tests", description = "Get all tests")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<TestResponse> getAll() {
        return testService.getAll();
    }

    @Operation(summary = "Delete test", description = "Delete test by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }

    @Operation(summary = "Update test", description = "Update test by id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TestResponse updateTest(@PathVariable Long id, @RequestBody TestRequest request) {
        return testService.updateTest(id, request);
    }
}