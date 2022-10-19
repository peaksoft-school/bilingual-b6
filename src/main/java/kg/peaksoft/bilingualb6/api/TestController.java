package kg.peaksoft.bilingualb6.api;

import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "*",maxAge = 3600)
public class TestController {

    private final TestService testService;

    @PutMapping("/enableDisable/{id}")
    public SimpleResponse enableDisable(@PathVariable Long id){
        return testService.enableDisable(id);
    }
}
