package am.registration.system.demo.security.token.key.provider;

import am.registration.system.demo.security.token.enums.TokenType;
import am.registration.system.demo.security.token.strategy.SigningKeyProviderStrategy;
import am.registration.system.demo.security.token.configuration.UserTokenProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides signing keys and signature algorithms for different types of tokens.
 * <p>
 * This class acts as a central provider for obtaining signing keys and algorithms
 * based on the specified {@link TokenType}. It supports multiple token types,
 * including JWT, password reset, and email verification tokens.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 19.02.25
 * Time: 00:44:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SigningKeyManager {

    private final JwtKeyProvider jwtKeyProvider;
    private final UserTokenProperties userTokenProperties;
    private final Map<TokenType, SigningKeyProviderStrategy> signingKeyProviders;

    /**
     * Constructs the SigningKeyManager and initializes the strategy map.
     * <p>
     * Uses a list of strategy implementations to build a map of supported token types.
     * </p>
     *
     * @param jwtKeyProvider       the JWT key provider
     * @param userTokenProperties  user token properties configuration
     * @param strategies           list of available signing key provider strategies
     */
    @Autowired
    public SigningKeyManager(JwtKeyProvider jwtKeyProvider, UserTokenProperties userTokenProperties, List<SigningKeyProviderStrategy> strategies) {
        this.jwtKeyProvider = jwtKeyProvider;
        this.userTokenProperties = userTokenProperties;
        this.signingKeyProviders = strategies.stream()
                .collect(Collectors.toMap(this::getTokenType, strategy -> strategy));
    }

    /**
     * Retrieves the signing key for the specified token type.
     *
     * @param type the token type
     * @return the signing key associated with the specified token type
     * @throws IllegalArgumentException if no signing key provider is found for the specified type
     */
    public Key retrieveSigningKey(TokenType type) {
        SigningKeyProviderStrategy strategy = signingKeyProviders.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("SigningKeyManager.class: No signing key provider found for type " + type);
        }
        return strategy.getSigningKey();
    }

    /**
     * Determines the token type supported by the given strategy.
     * <p>
     * Uses instanceof checks to map a strategy to a specific token type.
     * </p>
     *
     * @param strategy the signing key provider strategy
     * @return the token type supported by the strategy
     * @throws IllegalArgumentException if the strategy is not supported
     */
    private TokenType getTokenType(SigningKeyProviderStrategy strategy) {
        if (strategy instanceof JwtSigningKeyProvider) return TokenType.JSON_WEB_TOKEN;
        if (strategy instanceof PasswordResetSigningKeyProvider) return TokenType.PASSWORD_RESET;
        if (strategy instanceof EmailVerificationSigningKeyProvider) return TokenType.EMAIL_VERIFICATION;
        throw new IllegalArgumentException("Unsupported signing key provider strategy: " + strategy.getClass().getName());
    }

    /**
     * Retrieves the signature algorithm for the specified token type.
     * <p>
     * Uses strong algorithms suitable for each token type to ensure security.
     * </p>
     *
     * @param type the token type
     * @return the signature algorithm associated with the token type
     */
    public SignatureAlgorithm getSignatureAlgorithm(TokenType type) {
        return switch (type) {
            case JSON_WEB_TOKEN -> SignatureAlgorithm.ES256;
            case PASSWORD_RESET, EMAIL_VERIFICATION -> SignatureAlgorithm.HS256;
        };
    }

    /**
     * Retrieves the token expiration time for the specified token type.
     * <p>
     * The expiration time is configured differently for each token type,
     * based on application properties.
     * </p>
     *
     * @param type the token type
     * @return the expiration time in milliseconds
     */
    public Long retrieveTokenExpiration(TokenType type) {
        return switch (type) {
            case JSON_WEB_TOKEN -> jwtKeyProvider.getExpiration();
            case PASSWORD_RESET -> userTokenProperties.getPasswordResetTokenExpirationInMillis();
            case EMAIL_VERIFICATION -> userTokenProperties.getEmailVerificationTokenExpirationInMillis();
        };
    }
}