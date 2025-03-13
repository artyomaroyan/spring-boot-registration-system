package am.registration.system.demo.service.user.passwordreset;

import am.registration.system.demo.exception.InvalidPasswordResetRequestException;
import am.registration.system.demo.exception.InvalidTokenException;
import am.registration.system.demo.model.dto.PasswordResetRequest;
import am.registration.system.demo.service.user.validation.validator.PasswordValidator;
import am.registration.system.demo.service.user.validation.TokenValidatorService;
import am.registration.system.demo.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for validating password reset requests.
 * It ensures that the provided password meets validation criteria and that the reset token is valid.
 * *
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 00:23:57
 */
@Service
@RequiredArgsConstructor
class PasswordResetValidator {

    private final PasswordValidator passwordValidator;
    private final TokenValidatorService tokenValidatorService;

    /**
     * Validates the password reset request by checking the password format and verifying the reset token.
     *
     * @param request the password reset request containing the new password and reset token
     * @throws InvalidPasswordResetRequestException if the password format is invalid
     * @throws InvalidTokenException if the token is not valid or expired
     */
    protected void validatePasswordResetRequest(final PasswordResetRequest request) {
        if (!passwordValidator.isValid(request.password(), null)) {
            throw new InvalidPasswordResetRequestException(ExceptionMessages.INVALID_PASSWORD_RESET_REQUEST);
        }
        tokenValidatorService.validatePasswordResetToken(request.token());
    }
}