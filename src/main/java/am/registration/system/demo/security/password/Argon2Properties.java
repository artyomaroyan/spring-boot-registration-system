package am.registration.system.demo.security.password;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the Argon2 password hashing algorithm.
 * *
 * <p>This class loads configuration values from the application properties file.
 * It provides settings for memory usage, iterations, parallelism, hash length,
 * salt length, and a secret key to enhance security.</p>
 * *
 * <p>The values are injected using Spring's {@code @Value} annotation and are
 * expected to be present in the application configuration file under the prefix
 * {@code application.security.argon2}.</p>
 * *
 * Author: Artyom Aroyan
 * Date: 15.02.25
 */
@Getter
@Component
class Argon2Properties {

    @Value("${application.security.argon2.memory}")
    private int memory;
    @Value("${application.security.argon2.iterations}")
    private int iterations;
    @Value("${application.security.argon2.parallelism}")
    private int parallelism;
    @Value("${application.security.argon2.hashLength}")
    private int hashLength;
    @Value("${application.security.argon2.saltLength}")
    private int saltLength;
    @Value("${application.security.argon2.secretKey}")
    private String secret;
}