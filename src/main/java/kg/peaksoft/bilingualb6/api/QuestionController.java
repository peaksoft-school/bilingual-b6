package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.response.PaginationResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.SimpleResponse;
import kg.peaksoft.bilingualb6.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Question API", description = "The questions GET method by TEST id")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "Question status",
            description = "This endpoint returns all questions by Test id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PaginationResponse getPaginationOfAuthor(@RequestParam Long id,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return questionService.getTestById(id, page, size);
    }
}
