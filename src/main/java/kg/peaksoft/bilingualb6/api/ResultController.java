package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.dto.request.EvaluationRequest;
import kg.peaksoft.bilingualb6.dto.response.*;
import kg.peaksoft.bilingualb6.entites.AuthInfo;
import kg.peaksoft.bilingualb6.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/result")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Result API", description = "The result's GET methods")
public class ResultController {

    private final ResultService resultService;

    @Operation(summary = "Client results",
            description = "Authenticate client test results(for client)")
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping()
    public List<ResultResponses> getAll(Authentication authentication) {
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        return resultService.getAll(authInfo.getEmail());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Send result",
            description = "Send result to client")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String sendEmail(@PathVariable Long id) throws MessagingException {
        return resultService.sendResult(id);
    }

    @Operation(summary = "Delete result",
            description = "Client method that deletes its results(for client)")
    @PreAuthorize("hasAuthority('CLIENT')")
    @DeleteMapping("/{id}")
    public List<ResultResponses> delete(@PathVariable Long id, Principal principal){
       return resultService.deleteResult(id,principal);
    }

    @Operation(summary = "Get all results",
            description = "Get all clients results(for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/view-results")
    public List<ViewAllResultResponse> getAllResult(){
        return resultService.viewResults();
    }

    @Operation(summary = "Get client result",
            description = "Get the result of a specific client's question(for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/view-result-questions/{id}")
    public ViewResultResponse resultResponse(@PathVariable Long id){
        return resultService.getResultResponse(id);
    }

    @Operation(summary = "Get client answer",
            description = "Get client answer for evaluation(for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/view-client-answer/{id}")
    public CheckQuestionAnswerResponse answerResponse(@PathVariable Long id){
        return resultService.getAnswerForEvaluation(id);
    }

    @Operation(summary = "Evaluate client answer",
            description = "Evaluate client question answer and give a score (for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/evaluate-client-answer")
    public SimpleResponse viewResultResponse(@RequestBody EvaluationRequest request){
        return resultService.giveScoreForQuestion(request);
    }

    @Operation(summary = "Delete result",
            description = "Client method that deletes its results(for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/client-results/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return resultService.delete(id);
    }
}