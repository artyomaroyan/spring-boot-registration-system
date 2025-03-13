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
 * Generates tokens for email verification purposes.
 * <p>
 * This component implements the {@link TokenGenerationStrategy} interface and is responsible for
 * creating JSON Web Tokens (JWT) specifically for email verification. The tokens are signed
 * using the configured signing key and algorithm.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 21:09:39
 */
@Component
@RequiredArgsConstructor
@Qualifier("emailVerificationTokenGeneratorStrategy")
public class EmailVerificationTokenStrategy implements TokenGenerationStrategy {

    private final SigningKeyManager signingKeyManager;

    /**
     * Generates a signed JWT token for email verification.
     * <p>
     * Uses the signing key and algorithm specific to email verification tokens.
     * The generated token includes the provided claims and subject, as well as the
     * issued date and expiration date.
     * </p>
     *
     * @param claims  a map of claims to include in the token
     * @param subject the subject of the token (e.g., user's email or username)
     * @return a compact, signed JWT string
     */
    @Override
    public String generateToken(Map<String, Object> claims, String subject) {
        var type = TokenType.EMAIL_VERIFICATION;
        var signingKey = signingKeyManager.retrieveSigningKey(type);
        var algorithm = signingKeyManager.getSignatureAlgorithm(type);
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + signingKeyManager.retrieveTokenExpiration(type));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(signingKey, algorithm)
                .compact();
    }

    /**
     * Returns the token type supported by this strategy.
     *
     * @return the supported {@link TokenType} (EMAIL_VERIFICATION)
     */
    @Override
    public TokenType getSupportedTokenType() {
        return TokenType.EMAIL_VERIFICATION;
    }
}