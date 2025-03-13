package am.registration.system.demo.service.user.authentication;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.exception.InvalidTokenException;
import am.registration.system.demo.exception.TokenDeactivationException;
import am.registration.system.demo.exception.TokenNotFoundException;
import am.registration.system.demo.exception.UserActivationException;
import am.registration.system.demo.model.entity.UserToken;
import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.model.repository.UserTokenRepository;
import am.registration.system.demo.security.token.service.UserTokenService;
import am.registration.system.demo.service.user.validation.TokenValidatorService;
import am.registration.system.demo.util.ExceptionMessages;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling user email activation and token validation.
 * This service verifies the activation token, activates the user, and deactivates the token.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 03:26:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
class UserActivationHandler {

    private final UserRepository userRepository;
    private final UserTokenService userTokenService;
    private final UserTokenRepository userTokenRepository;
    private final TokenValidatorService tokenValidatorService;

    /**
     * Activates the user's email by validating the provided token and updating the user state.
     *
     * @param token the activation token
     * @return ApiResponseBuilder<String> containing the success message
     * @throws TokenNotFoundException if the token is not found in the database
     * @throws InvalidTokenException if the token is invalid or expired
     * @throws UserActivationException if activation of the user fails
     * @throws TokenDeactivationException if deactivation of the token fails
     */
    protected ApiResponseBuilder<String> activateUserAccount(final String token) {
        var activationToken = fetchActivationToken(token);
        validateActivationToken(activationToken);
        activateUserAndInvalidateToken(activationToken);
        return ApiResponse.success(LogMessages.ACTIVATION_SUCCEEDED);
    }

    /**
     * Retrieves the activation token from the database.
     *
     * @param token the activation token string
     * @return the UserToken entity associated with the token
     * @throws TokenNotFoundException if the token is not found
     */
    private UserToken fetchActivationToken(final String token) {
        return userTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(ExceptionMessages.TOKEN_NOT_FOUND));
    }

    /**
     * Validates the activation token to ensure it is valid and not expired.
     *
     * @param token the UserToken to be validated
     * @throws InvalidTokenException if the activation token is invalid or expired
     */
    private void validateActivationToken(final UserToken token) {
        if (isInvalidActivationToken(token)) {
            log.info(LogMessages.INVALID_ACTIVATION_TOKEN);
            throw new InvalidTokenException(ExceptionMessages.INVALID_ACTIVATION_TOKEN);
        }
    }

    /**
     * Checks whether the provided activation token is valid.
     *
     * @param token the UserToken to be checked
     * @return true if the token is invalid, false otherwise
     */
    private boolean isInvalidActivationToken(final UserToken token) {
        return !tokenValidatorService.isValidVerificationToken(token.getToken());
    }

    /**
     * Activates the user associated with the token and deactivates the token afterward.
     *
     * @param token the UserToken associated with the user to be activated
     * @throws UserActivationException if the user activation process fails
     * @throws TokenDeactivationException if the token deactivation process fails
     */
    private void activateUserAndInvalidateToken(final UserToken token) {
        try {
            var user = token.getUser();
            userRepository.updateUserState(user.getId());
            userTokenService.invalidateToken(token);
        } catch (UserActivationException ex) {
            log.debug(LogMessages.ACTIVATION_FAILED, ex.getMessage());
            throw new UserActivationException(String.format(ExceptionMessages.ACTIVATION_FAILED_WITH_REASON, token.getToken(), ex.getCause()), ex.getCause());
        } catch (TokenDeactivationException ex) {
            log.debug(LogMessages.TOKEN_DEACTIVATION_FAILED, ex.getMessage());
            throw new TokenDeactivationException(String.format(ExceptionMessages.TOKEN_DEACTIVATION_FAILED_WITH_REASON, token.getToken(), ex.getCause()), ex.getCause());
        }
    }
}