package am.registration.system.demo.util;

/**
 * A utility class that holds constant log message strings used throughout the application.
 * These messages are primarily intended for structured logging and user notifications.
 * *
 * * Usage:
 * - Provides a centralized location for consistent and maintainable log messages.
 * - Supports formatted messages using parameter placeholders (e.g., {@code {}}) where applicable.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 14:45:28
 */
public class LogMessages {
    public static final String SUCCESSFULLY_REGISTERED = "You have successfully registered:";
    public static final String REGISTRATION_FAILED = "User registration failed: {}";
    public static final String ACTIVATION_FAILED = "User activation failed: {}";
    public static final String ACTIVATION_SUCCEEDED = "User successfully activated:";
    public static final String INVALID_ACTIVATION_TOKEN = "Invalid activation token: {}";
    public static final String TOKEN_DEACTIVATION_FAILED = "Token deactivation failed: {}";
    public static final String USER_SUCCESSFULLY_UPDATED = "You have successfully updated: ";
    public static final String NO_USER_FOUND = "No such user found";
    public static final String SUCCESS_RESPONSE = "Success";
    public static final String BLANKNESS_PASSWORD = "Password can not be null or empty";
    public static final String INVALID_PASSWORD_FORMAT = "Password does not meet the required format";
    public static final String BLANKNESS_EMAIL = "Email can not be null or empty";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format: ";
    public static final String USED_EMAIL = "Email {} already exists:";
    public static final String BLANKNESS_PHONE = "Phone number can not be null or empty";
    public static final String INVALID_NUMBER_LENGTH = "Phone number length must be between 9 and 12";
    public static final String INVALID_CHARACTERS = "Phone number contains invalid characters: {}";
    public static final String BLANKNESS_USERNAME = "Username can not be null or empty:";
    public static final String INVALID_USERNAME_LENGTH = "Username must be between 5 and 20 characters:";
    public static final String USED_USERNAME = "Username {} is already in use: ";
    public static final String INVALID_EMAIL = "Invalid email:";
    public static final String INVALID_PHONE = "Invalid phone number:";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String INVALID_USERNAME = "Invalid username";
    public static final String EMAIL_SUCCESSFULLY_SENT = "Email successfully sent to {}";
    public static final String FAILED_TO_SEND_EMAIL = "Email failed to send to {}: {}";
    public static final String PASSWORD_RESET_SUCCESS = "Your password has been successfully reset:";
    public static final String GENERATE_NEW_TOKEN = "Generating new token for user: {}";
    public static final String START_SCHEDULE = "Running scheduled task to update expired tokens...";
    public static final String FINISH_SCHEDULE = "Finished scheduled task. {} tokens marked as FORCIBLY_EXPIRED";
    public static final String DELETED = "Your account was successfully deleted";
    public static final String USER_TOKEN_NOT_FOUND = "User token was not found";
    public static final String INVALID_TOKEN_PURPOSE = "Token verification failed: Invalid purpose";
    public static final String INVALID_TOKEN_STATE = "Token verification failed: Token is not in PENDING state";
    public static final String EXPIRED_TOKEN = "Token verification failed: Token expired";
    public static final String TOKEN_VALIDATION_SUCCESS = "Token successfully verified:";
    public static final String INVALID_TOKEN_SIGNATURE = "Invalid signature or malformed token: {}";
    public static final String UNSUPPORTED_TOKEN = "Unsupported token: {}";
    public static final String EMPTY_TOKEN = "Token can not be empty:";
}