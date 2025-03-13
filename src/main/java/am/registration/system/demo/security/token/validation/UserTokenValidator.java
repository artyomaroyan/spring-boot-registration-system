package am.registration.system.demo.security.token.validation;

import am.registration.system.demo.model.entity.UserToken;
import am.registration.system.demo.model.repository.UserTokenRepository;
import am.registration.system.demo.security.token.enums.TokenPurpose;
import am.registration.system.demo.security.token.enums.TokenState;
import am.registration.system.demo.security.token.enums.TokenType;
import am.registration.system.demo.security.token.key.provider.SigningKeyManager;
import am.registration.system.demo.util.LogMessages;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

/**
 * Validator for user tokens, specifically for email verification tokens.
 * <p>
 * This component is responsible for validating email verification tokens by checking their existence,
 * purpose, state, expiration, and signature validity.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 03.03.25
 * Time: 00:44:16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenValidator {

    private final SigningKeyManager signingKeyManager;
    private final ExtractTokenClaims extractTokenClaims;
    private final UserTokenRepository userTokenRepository;

    /**
     * Verifies the validity of an email verification token.
     *
     * @param token the email verification token to be validated
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    public boolean validateEmailVerificationToken(final String token) {
        return verifyToken(token);
    }

    /**
     * Verifies the validity of a given token, including its existence, purpose, state, expiration,
     * and digital signature.
     *
     * @param token the token to be validated
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    private boolean verifyToken(String token) {
        Optional<UserToken> userTokenOptional = userTokenRepository.findByToken(token);
        if (userTokenOptional.isEmpty()) {
            log.error(LogMessages.USER_TOKEN_NOT_FOUND);
            return false;
        }

        UserToken userToken = userTokenOptional.get();
        if (!userToken.getTokenPurpose().equals(TokenPurpose.ACCOUNT_VERIFICATION)) {
            log.error(LogMessages.INVALID_TOKEN_PURPOSE);
            return false;
        }

        if (!userToken.getTokenState().equals(TokenState.PENDING)) {
            log.error(LogMessages.INVALID_TOKEN_STATE);
            return false;
        }

        if (userToken.getExpireDate().before(new Date())) {
            log.error(LogMessages.EXPIRED_TOKEN);
            return false;
        }

        try {
            Key key = signingKeyManager.retrieveSigningKey(TokenType.EMAIL_VERIFICATION);
            extractTokenClaims.extractClaimsFormToken(token, key);
            log.info(LogMessages.TOKEN_VALIDATION_SUCCESS);
            return true;
        } catch (SecurityException | MalformedJwtException ex) {
            log.error(LogMessages.INVALID_TOKEN_SIGNATURE, ex);
        } catch (ExpiredJwtException ex) {
            log.error(LogMessages.EXPIRED_TOKEN, ex);
        } catch (UnsupportedJwtException ex) {
            log.error(LogMessages.UNSUPPORTED_TOKEN, ex);
        } catch (IllegalArgumentException ex) {
            log.error(LogMessages.EMPTY_TOKEN, ex);
        }
        return false;
    }
}