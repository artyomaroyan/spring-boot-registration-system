package am.registration.system.demo.service.user.passwordreset;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.exception.InvalidPasswordResetRequestException;
import am.registration.system.demo.model.dto.PasswordResetRequest;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.security.password.Argon2Hashing;
import am.registration.system.demo.security.token.service.UserTokenService;
import am.registration.system.demo.security.token.validation.PasswordResetUserResolver;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Component responsible for handling password reset operations,
 * including password validation, updating, and token invalidation.
 * *
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 01:01:58
 */
@Component
@RequiredArgsConstructor
class PasswordResetHandler {

    private final Argon2Hashing argon2Hashing;
    private final UserRepository userRepository;
    private final UserTokenService userTokenService;
    private final PasswordResetValidator passwordResetValidator;
    private final PasswordResetUserResolver passwordResetUserResolver;

    /**
     * Resets the password for the user associated with the given request.
     *
     * @param request the password reset request containing the token and new password
     * @return an ApiResponseBuilder indicating the result of the password reset operation
     * @throws InvalidPasswordResetRequestException if the request is invalid
     */
    protected ApiResponseBuilder<String> processPasswordReset(final PasswordResetRequest request) {
        passwordResetValidator.validatePasswordResetRequest(request);
        var user = passwordResetUserResolver.extractUserFromToken(request.token());
        updatePassword(user, request.password());
        user.getUserToken().forEach(userTokenService::invalidateToken);
        return ApiResponse.success(LogMessages.PASSWORD_RESET_SUCCESS);
    }

    /**
     * Updates the user's password with the given new password.
     *
     * @param user the user whose password will be updated
     * @param password the new password to be encoded and set
     */
    private void updatePassword(User user, String password) {
        user.setPassword(argon2Hashing.encode(password));
        userRepository.saveAndFlush(user);
    }
}