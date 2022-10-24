package kg.peaksoft.bilingualb6.config.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Getter
@Setter
@Configuration
public class S3Config {

    @Value("${aws.bucket.access_key_id}")
    private String AWS_ACCESS_KEY;

    @Value("${aws.bucket.secret_access_key}")
    private String AWS_SECRET_KEY;

    @Value("${aws.bucket.region}")
    private String REGION;

    @Bean
    S3Client s3Client() {

        Region region = Region.of(REGION);

        final AwsBasicCredentials credentials = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
