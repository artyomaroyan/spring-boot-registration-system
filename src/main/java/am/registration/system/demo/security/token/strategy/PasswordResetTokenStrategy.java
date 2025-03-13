package am.registration.system.demo.security.token.strategy;

import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.security.token.enums.TokenType;
import am.registration.system.demo.security.token.key.provider.SigningKeyManager;
import am.registration.system.demo.service.user.management.UserManagementService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Generates password reset tokens using JWT.
 * <p>
 * This component implements the {@link TokenGenerationStrategy} interface to provide
 * token generation logic specifically for password reset tokens. It uses JWT to
 * sign and generate the tokens with appropriate claims and expiration times.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 19.02.25
 * Time: 01:22:31
 */
@Component
@RequiredArgsConstructor
@Qualifier("passwordResetTokenGeneratorStrategy")
public class PasswordResetTokenStrategy implements TokenGenerationStrategy {

    private final SigningKeyManager signingKeyManager;
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;

    /**
     * Generates a JWT password reset token with the specified claims and subject.
     * <p>
     * The token is generated using the signing key and algorithm specified for password reset tokens.
     * It includes user roles as additional claims and has an expiration date.
     * </p>
     *
     * @param claims  the additional claims to include in the token
     * @param subject the subject (username) of the token
     * @return the generated JWT password reset token
     */
    @Override
    public String generateToken(Map<String, Object> claims, String subject) {
        var type = TokenType.PASSWORD_RESET;
        var signingKey = signingKeyManager.retrieveSigningKey(type);
        var algorithm = signingKeyManager.getSignatureAlgorithm(type);
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + signingKeyManager.retrieveTokenExpiration(type));
        var user = userMapper.mapFromResponseToEntity(userManagementService.getUserByUsername(subject).data());

        return Jwts.builder()
                .setClaims(claims)
                .claim("Roles", user.getRoles())
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signingKey, algorithm)
                .compact();
    }

    /**
     * Returns the supported token type for this strategy.
     *
     * @return the supported {@link TokenType} (PASSWORD_RESET)
     */
    @Override
    public TokenType getSupportedTokenType() {
        return TokenType.PASSWORD_RESET;
    }
}