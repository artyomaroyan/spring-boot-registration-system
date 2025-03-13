package am.registration.system.demo.security.token.strategy;

import am.registration.system.demo.security.token.enums.TokenType;
import am.registration.system.demo.security.token.key.provider.SigningKeyManager;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Implementation of {@link TokenGenerationStrategy} for generating JSON Web Tokens (JWT).
 * <p>
 * This strategy uses signing keys and algorithms provided by the {@link SigningKeyManager}
 * to generate secure JWT tokens with embedded claims. The generated token includes
 * information such as subject, issue date, expiration date, and other specified claims.
 * </p>
 * *
 * Author: Artyom Aroyan
 * Date: 19.02.25
 * Time: 01:46:20
 */
@Component
@RequiredArgsConstructor
@Qualifier("jwtTokenStrategy")
public class JwtTokenStrategy implements TokenGenerationStrategy {

    private final SigningKeyManager signingKeyManager;

    /**
     * Generates a JSON Web Token (JWT) with the specified claims and subject.
     * <p>
     * Uses the signing key and algorithm specific to the {@link TokenType#JSON_WEB_TOKEN}.
     * Sets the issued date and expiration date based on the configured token lifespan.
     * </p>
     *
     * @param claims  A map of claims to be embedded in the JWT.
     * @param subject The subject for whom the token is being generated.
     * @return A compact, URL-safe JWT string.
     */
    @Override
    public String generateToken(Map<String, Object> claims, String subject) {
        var type = TokenType.JSON_WEB_TOKEN;
        var signingKey = signingKeyManager.retrieveSigningKey(type);
        var algorithm = signingKeyManager.getSignatureAlgorithm(type);
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + signingKeyManager.retrieveTokenExpiration(type));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signingKey, algorithm)
                .compact();
    }

    /**
     * Returns the supported token type handled by this strategy.
     *
     * @return The {@link TokenType} supported by this strategy, which is {@code JSON_WEB_TOKEN}.
     */
    @Override
    public TokenType getSupportedTokenType() {
        return TokenType.JSON_WEB_TOKEN;
    }
}