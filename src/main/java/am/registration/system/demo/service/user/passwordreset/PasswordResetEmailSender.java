package am.registration.system.demo.service.user.passwordreset;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.configuration.application.LinkConfiguration;
import am.registration.system.demo.email.EmailSender;
import am.registration.system.demo.exception.EmailValidationException;
import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.entity.UserToken;
import am.registration.system.demo.security.token.service.UserTokenService;
import am.registration.system.demo.service.user.management.UserManagementService;
import am.registration.system.demo.util.ExceptionMessages;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling password reset email operations,
 * including generating a reset token and sending a password reset email.
 * *
 * Author: Artyom Aroyan
 * Date: 23.02.25
 * Time: 00:06:54
 */
@Service
@RequiredArgsConstructor
class PasswordResetEmailSender {

    private final UserMapper userMapper;
    private final EmailSender emailSender;
    private final UserTokenService userTokenService;
    private final UserManagementService userManagementService;

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email the email address to which the password reset link will be sent
     * @return an ApiResponseBuilder containing the result of the email sending operation
     * @throws EmailValidationException if the email format is invalid or the email does not exist
     */
    protected ApiResponseBuilder<String> sendPasswordResetEmail(final String email) {
        var token = generatePasswordResetToken(email);
        var resetLink = LinkConfiguration.passwordResetLink(token.getToken());
        var subject = LinkConfiguration.PASSWORD_RESET_EMAIL_SUBJECT;
        emailSender.send(email, subject, resetLink);
        return ApiResponse.success(LogMessages.EMAIL_SUCCESSFULLY_SENT);
    }


    /**
     * Generates a password reset token for the given email.
     *
     * @param email the email address for which to generate a reset token
     * @return the generated UserToken object
     * @throws EmailValidationException if the email validation fails
     */
    private UserToken generatePasswordResetToken(final String email) {
        var user = fetchUserByEmail(email);
        return userTokenService.createPasswordResetToken(user.getEmail());
    }

    /**
     * Fetches the user associated with the specified email.
     *
     * @param email the email address to fetch the user for
     * @return the User object associated with the email
     * @throws EmailValidationException if the email is invalid or does not correspond to any user
     */
    private User fetchUserByEmail(final String email) {
        if (!isEmailValid(email)) {
            throw new EmailValidationException(ExceptionMessages.INVALID_EMAIL);
        }
        return userMapper.mapFromResponseToEntity(userManagementService.getUserByEmail(email).data());
    }

    /**
     * Validates whether the given email exists in the system.
     *
     * @param email the email address to validatePasswordResetRequest
     * @return true if the email exists and is valid, false otherwise
     */
    private boolean isEmailValid(final String email) {
        return userManagementService.getUserByEmail(email).success();
    }
}