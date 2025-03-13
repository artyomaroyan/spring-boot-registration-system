package am.registration.system.demo.security.token.service;

import am.registration.system.demo.security.token.enums.TokenType;
import am.registration.system.demo.security.token.strategy.TokenStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Responsible for generating tokens based on the provided token type.
 * Uses a strategy pattern to delegate the actual token generation to the appropriate implementation.
 * *
 * The @Component annotation allows Spring to detect and manage this class as a bean.
 * The @RequiredArgsConstructor annotation automatically generates a constructor for all final fields,
 * promoting immutability and dependency injection.
 * *
 * Author: Artyom Aroyan
 * Date: 20.02.25
 * Time: 00:53:56
 */
@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final TokenStrategyFactory strategyFactory;

    /**
     * Creates a token using the appropriate generation strategy based on the token type.
     *
     * @param claims  a map of claims to be included in the token
     * @param subject the subject for which the token is being generated
     * @param type    the type of token to generate
     * @return the generated token as a string
     * @throws IllegalArgumentException if no strategy is found for the given token type
     */
    public String createToken(Map<String, Object> claims, String subject, TokenType type) {
        return Optional.ofNullable(strategyFactory.getTokenGenerationStrategy(type))
                .map(strategy -> strategy.generateToken(claims, subject))
                .orElseThrow(() -> new IllegalArgumentException("No strategy found for token type " + type));
    }
}