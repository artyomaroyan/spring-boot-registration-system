package am.registration.system.demo.service.user.validation;

import am.registration.system.demo.exception.InvalidTokenException;
import am.registration.system.demo.exception.TokenNotFoundException;
import am.registration.system.demo.model.entity.UserToken;
import am.registration.system.demo.model.repository.UserTokenRepository;
import am.registration.system.demo.security.token.enums.TokenPurpose;
import am.registration.system.demo.security.token.enums.TokenState;
import am.registration.system.demo.security.token.validation.UserTokenValidator;
import am.registration.system.demo.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Service class responsible for validating user tokens, including password reset and email verification tokens.
 * Uses the UserTokenRepository to fetch tokens and the UserTokenValidator for token validation.
 * *
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 00:45:43
 */
@Component
@RequiredArgsConstructor
public class TokenValidatorService {

    private final UserTokenRepository userTokenRepository;
    private final UserTokenValidator userTokenValidator;

    /**
     * Validates a password reset token by checking its existence, validity, and purpose.
     * Throws an InvalidTokenException if the token is invalid or expired.
     *
     * @param token the password reset token to validatePasswordResetRequest
     * @throws InvalidTokenException if the token is invalid or expired
     */
    public void validatePasswordResetToken(final String token) {
        var userToken = fetchUserToken(token);
        if (isTokenInvalid(userToken) || userToken.getTokenPurpose() != TokenPurpose.PASSWORD_RECOVERY) {
            throw new InvalidTokenException(ExceptionMessages.INVALID_OR_EXPIRED_TOKEN);
        }
    }

    /**
     * Verifies the validity of an email verification token.
     *
     * @param token the email verification token to validatePasswordResetRequest
     * @return true if the token is valid, false otherwise
     */
    public boolean isValidVerificationToken(final String token) {
       return userTokenValidator.validateEmailVerificationToken(token);
    }

    /**
     * Fetches a user token from the repository.
     * Throws a TokenNotFoundException if the token does not exist.
     *
     * @param token the token string to look up
     * @return the UserToken object if found
     * @throws TokenNotFoundException if the token is not found
     */
    private UserToken fetchUserToken(final String token) {
        return userTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(ExceptionMessages.TOKEN_NOT_FOUND));
    }

    /**
     * Checks whether a token is invalid based on its state, expiration date, or null value.
     *
     * @param token the UserToken to validatePasswordResetRequest
     * @return true if the token is invalid, false otherwise
     */
    private boolean isTokenInvalid(UserToken token) {
        return token.getToken() == null ||
                token.getExpireDate().toInstant().isBefore(Instant.from(Instant.now())) ||
                token.getTokenState() != TokenState.PENDING;
    }
}