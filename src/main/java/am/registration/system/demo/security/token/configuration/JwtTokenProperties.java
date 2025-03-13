package am.registration.system.demo.security.token.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for JWT (JSON Web Token) settings.
 * These properties are loaded from the application's configuration file
 * (e.g., application.yml or application.properties) with the prefix "application.security.jwt".
 * *
 * * Properties include:
 * - A key path, password, and alias for loading JWT signing keys.
 * - Expiration time for JWT tokens.
 * *
 * * Usage:
 * The properties are injected automatically into beans using the @EnableConfigurationProperties(JwtTokenProperties.class) annotation.
 * *
 * Author: Artyom Aroyan
 * Date: 18.02.25
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtTokenProperties {
    private String keystorePath;
    private String keystorePassword;
    private String keystoreAlias;
    private Long expiration;
}