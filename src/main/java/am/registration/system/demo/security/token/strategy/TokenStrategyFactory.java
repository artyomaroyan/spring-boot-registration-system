package am.registration.system.demo.security.token.strategy;

import am.registration.system.demo.security.token.enums.TokenType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory for retrieving the appropriate token generation strategy based on the token type.
 * This class collects all available implementations of the TokenGenerationStrategy interface
 * and provides a way to get the correct strategy for a given token type.
 *  *
 * The @Component annotation allows Spring to detect and manage this factory as a bean.
 * The constructor automatically collects all available strategies and maps them by token type.
 * *
 * Author: Artyom Aroyan
 * Date: 20.02.25
 * Time: 00:49:20
 */
@Component
public class TokenStrategyFactory {

    private final Map<TokenType, TokenGenerationStrategy> strategies;

    /**
     * Constructs the factory and initializes the strategy map.
     * Uses a list of available strategies to build a map where the key is the token type
     * and the value is the corresponding strategy.
     *
     * @param strategiesList a list of available token generation strategies
     */
    public TokenStrategyFactory(List<TokenGenerationStrategy> strategiesList) {
        this.strategies = strategiesList.stream()
                .collect(Collectors.toMap(TokenGenerationStrategy::getSupportedTokenType, Function.identity()));
    }

    /**
     * Retrieves the token generation strategy for the given token type.
     *
     * @param type the type of token for which the strategy is required
     * @return the appropriate TokenGenerationStrategy, or null if no strategy is found
     */
    public TokenGenerationStrategy getTokenGenerationStrategy(TokenType type) {
        return strategies.get(type);
    }
}