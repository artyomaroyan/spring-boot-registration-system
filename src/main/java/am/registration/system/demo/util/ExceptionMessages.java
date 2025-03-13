package am.registration.system.demo.util;

/**
 * A utility class that holds constant error message strings used throughout the application.
 * These messages are primarily intended for exception handling and logging purposes.
 * *
 * * Usage:
 * - Provides a centralized location for consistent and maintainable exception messages.
 * - Supports formatted messages using {@code String.format()} where applicable.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 14:46:40
 */
public class ExceptionMessages {
    public static final String ACTIVATION_FAILED_WITH_REASON = "Failed to activate user: %s: %s";
    public static final String TOKEN_NOT_FOUND = "Activation token not found:";
    public static final String INVALID_ACTIVATION_TOKEN = "Invalid activation token:";
    public static final String TOKEN_DEACTIVATION_FAILED_WITH_REASON = "Token deactivation failed: %s: %s";
    public static final String REGISTRATION_FAILED = "User registration failed: ";
    public static final String INVALID_PASSWORD_RESET_REQUEST = "Invalid password reset request:";
    public static final String INVALID_OR_EXPIRED_TOKEN = "Invalid or expired token:";
    public static final String INVALID_EMAIL = "Failed to validatePasswordResetRequest email:";
}