package am.registration.system.demo.security.token.strategy;

import am.registration.system.demo.security.token.enums.TokenType;

import java.util.Map;

/**
 * A strategy interface for generating tokens with specific claims and types.
 * Implementations of this interface provide methods to generate tokens and specify
 * the token type they support.
 * *
 * Author: Artyom Aroyan
 * Date: 19.02.25
 * Time: 01:02:29
 */
public interface TokenGenerationStrategy {
    String generateToken(Map<String, Object> claims, String subject);
    TokenType getSupportedTokenType();
}