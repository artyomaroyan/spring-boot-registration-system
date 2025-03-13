package am.registration.system.demo.configuration.swagger;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

/**
 * Configuration class for setting up Swagger (OpenAPI) documentation.
 * <p>
 * This configuration defines security schemes for both Basic Authentication
 * and Bearer Token (JWT) authentication. The generated documentation provides
 * comprehensive API information, including authentication requirements.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 25.02.25
 * Time: 01:28:59
 */
@Configuration
@SecurityScheme(name = "basicAuthentication", type = HTTP, scheme = "basic")
@SecurityScheme(name = "bearerAuthentication", type = HTTP, scheme = "bearer", bearerFormat = "JSON_WEB_TOKEN")
public class SwaggerConfiguration {

    /**
     * Creates an OpenAPI bean to configure Swagger documentation.
     * <p>
     * Sets up API information, including title, version, and description.
     * Also defines security schemes for Basic and Bearer authentication.
     * </p>
     *
     * @return an OpenAPI object with configured API information and security schemes
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("Registration System API")
                        .version("1.0")
                        .description("Registration System API"))
                .components(new Components()
                        .addSecuritySchemes("basicAuthentication", basicAuthentication())
                        .addSecuritySchemes("bearerAuthentication", bearerAuthentication()))
                .addSecurityItem(new SecurityRequirement().addList("basicAuthentication"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuthentication"));
    }

    /**
     * Defines the Basic Authentication security scheme.
     * <p>
     * This scheme allows clients to authenticate using HTTP Basic Auth.
     * </p>
     *
     * @return a SecurityScheme object representing Basic Authentication
     */
    private io.swagger.v3.oas.models.security.SecurityScheme basicAuthentication() {
        return new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .scheme("basic");
    }

    /**
     * Defines the Bearer Token (JWT) authentication scheme.
     * <p>
     * This scheme allows clients to authenticate using JSON Web Tokens.
     * </p>
     *
     * @return a SecurityScheme object representing Bearer Token authentication
     */
    private io.swagger.v3.oas.models.security.SecurityScheme bearerAuthentication() {
        return new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JSON_WEB_TOKEN");
    }
}