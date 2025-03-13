package am.registration.system.demo.service.user.passwordreset;

import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.model.dto.PasswordResetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling password reset operations, including
 * resetting the user's password and sending a password reset email.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 22:03:14
 */
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetHandler passwordResetHandler;
    private final PasswordResetEmailSender passwordResetEmailSender;

    /**
     * Resets the user's password based on the given reset request.
     *
     * @param request the password reset request containing the new password and reset token
     * @return an ApiResponseBuilder containing the result of the password reset operation
     */
    public ApiResponseBuilder<String> resetPassword(final PasswordResetRequest request) {
        return passwordResetHandler.processPasswordReset(request);
    }

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email the email address to send the password reset link to
     * @return an ApiResponseBuilder containing the result of the email sending operation
     */
    public ApiResponseBuilder<String> sendPasswordResetEmail(final String email) {
        return passwordResetEmailSender.sendPasswordResetEmail(email);
    }
}