package kg.peaksoft.bilingualb6.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    private static final String API_KEY = "Bearer Token";

    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().
                        addSecuritySchemes(API_KEY, apiKeySecuritySchema())).info(new Info().
                        title("Bilingual")).
                security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));
    }

    private SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme().name("Authorization").description("Just put the token").
                in(SecurityScheme.In.HEADER).type(SecurityScheme.Type.HTTP).scheme("Bearer");
    }
}
