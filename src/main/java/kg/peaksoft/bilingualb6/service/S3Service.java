package kg.peaksoft.bilingualb6.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Map;

@Service
@Getter @Setter
@Slf4j
public class S3Service {
    private final S3Client s3;

    @Value("${aws.bucket.name}")
    private String BUCKET_NAME;

    @Value("${aws.bucket.path}")
    private String BUCKET_PATH;

    @Autowired
    public S3Service(S3Client s3) {
        this.s3 = s3;
    }


    public Map<String, String> upload(MultipartFile file) throws IOException {
        log.info("Uploading file ...");
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();
        s3.putObject(put, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        log.info("Upload completed");
        return Map.of(
                "link", BUCKET_PATH + key);
    }

    public Map<String, String> delete(String fileLink) {

        log.info("Deleting file...");

        try {
            String key = fileLink.substring(BUCKET_PATH.length());
            log.warn("Deleting object: {}", key);

            s3.deleteObject(dor -> dor.bucket(BUCKET_NAME).key(key).build()); //dor = DeleteObjectRequest

        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return Map.of(
                "message", fileLink + " has been deleted");
    }
}

