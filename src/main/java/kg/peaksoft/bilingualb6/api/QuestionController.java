package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.request.QuestionRequest;
import kg.peaksoft.bilingualb6.dto.request.QuestionUpdateRequest;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.service.QuestionService;
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

    @Operation(summary = "Save question",
            description = "The save question with different types and options")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid QuestionRequest questionRequest){
        return questionService.save(questionRequest);
    }

    @Operation(summary = "Question status option",
            description = "This endpoint returns test status to enable and disable for test further requests to the API")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/enable-disable/{id}")
    public SimpleResponse enableDisable(@PathVariable Long id){
        return questionService.enableDisable(id);
    }

    @Operation(summary = "The question delete method",
            description = "The delete method by question id for question")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return questionService.delete(id);
    }

    @Operation(summary = "The question update method",
            description = "The update method by question id for question")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody QuestionUpdateRequest questionUpdateRequest) {
        return questionService.update(id,questionUpdateRequest);
    }
}
