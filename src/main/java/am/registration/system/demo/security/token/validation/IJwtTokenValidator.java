package am.registration.system.demo.security.token.validation;

/**
 * Interface for validating JWT tokens.
 * Provides a method to verify the integrity and authenticity of a given token
 * based on the expected username.
 * *
 * This interface follows the strategy pattern, allowing different implementations
 * of JWT token validation to be used interchangeably.
 * *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 20:42:32
 */
public interface IJwtTokenValidator {
    boolean validateToken(String token, String username);
}