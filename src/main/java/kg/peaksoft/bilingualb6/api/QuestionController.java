package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionUpdateResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.service.QuestionService;
import kg.peaksoft.bilingualb6.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Question API", description = "The question's CRUD methods")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "This method for save question",
            description = "The save question with different types and options")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid QuestionRequest questionRequest){
        return questionService.save(questionRequest);
    }

    @Operation(summary = "This method for get question by id",
            description = "With this endpoint we can get question by id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public QuestionResponse getQuestionById(@PathVariable Long id){
        return questionService.getQuestionById(id);
    }

    @Operation(summary = "This method for enable-disable question",
            description = "This endpoint returns question's status to enable and disable for test further requests to the API")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/enable-disable/{id}")
    public SimpleResponse enableDisable(@PathVariable Long id){
        return questionService.enableDisable(id);
    }

    @Operation(summary = "This method for delete question",
            description = "The delete method by question id for question")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return questionService.delete(id);
    }

    @Operation(summary = "This method for update question",
            description = "The update method by question id for question")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public QuestionUpdateResponse update(@PathVariable Long id, @RequestBody QuestionUpdateRequest questionUpdateRequest) {
        return questionService.update(id,questionUpdateRequest);
    }
}
