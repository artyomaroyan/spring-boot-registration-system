package am.registration.system.demo.configuration.application;

import am.registration.system.demo.security.token.enums.TokenPurpose;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for generating verification and password reset links.
 * <p>
 * This class centralizes the logic of link creation to ensure consistency
 * in generating URLs for account verification and password recovery.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 02:43:11
 */
@Configuration
public class LinkConfiguration {

    private static final String BASE_URL = "http://localhost:8080/";
    public static final String VERIFICATION_EMAIL_SUBJECT = "User verification email";
    public static final String PASSWORD_RESET_EMAIL_SUBJECT = "Password reset email";

    /**
     * Generates an account verification link for the user.
     * <p>
     * The generated link is intended to be sent via email to allow users
     * to verify their account by following the link.
     * </p>
     *
     * @param token the verification token
     * @return the complete verification link
     */
    public static String emailVerificationLink(String token) {
        return "Follow this link to verify your profile:" + createLink(token, TokenPurpose.ACCOUNT_VERIFICATION);
    }

    /**
     * Generates a password reset link for the user.
     * <p>
     * The generated link is intended to be sent via email to allow users
     * to reset their password by following the link.
     * </p>
     *
     * @param token the password reset token
     * @return the complete password reset link
     */
    public static String passwordResetLink(String token) {
        return "Please click the link below to reset your password" + createLink(token, TokenPurpose.PASSWORD_RECOVERY);
    }

    /**
     * Constructs a URL for the specified token and purpose.
     * <p>
     * Uses the base URL and the given token and purpose to build the link.
     * </p>
     *
     * @param token the token to be embedded in the link
     * @param purpose the purpose of the token (verification or password recovery)
     * @return the constructed link
     */
    private static String createLink(String token, TokenPurpose purpose) {
        return String.format("%s/api/v1/user/ex8/verify-token?token=%s&token_purpose=%s", BASE_URL, token, purpose);
    }
}