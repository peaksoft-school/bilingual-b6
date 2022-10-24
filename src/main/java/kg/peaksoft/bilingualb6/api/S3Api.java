package kg.peaksoft.bilingualb6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb6.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/static")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Storage Api", description = "Upload and delete files")

public class S3Api {

    private final S3Service s3Service;

    @Autowired
    public S3Api(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Operation(summary = "Upload file", description = "Upload file to database")
    @PostMapping(
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, String> upload(@RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        return s3Service.upload(file);
    }

    @Operation(summary = "Delete file", description = "Delete file from database")
    @DeleteMapping()
    Map<String, String> delete(@RequestParam String fileLink) {
        return s3Service.delete(fileLink);
    }
}
