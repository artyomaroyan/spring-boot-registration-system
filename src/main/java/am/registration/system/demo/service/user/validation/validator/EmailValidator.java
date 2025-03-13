package am.registration.system.demo.service.user.validation.validator;

import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.service.user.validation.annotation.ValidEmail;
import am.registration.system.demo.util.LogMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Validates email addresses for format correctness, uniqueness, and non-blankness.
 * Uses a regular expression to ensure the email format is valid.
 * Logs appropriate error messages when validation fails.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 00:29:44
 */
@Component
@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Logger log = LoggerFactory.getLogger(EmailValidator.class);
    private final UserRepository userRepository;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates the given email address.
     *
     * @param email   the email address to validatePasswordResetRequest
     * @param context the validation context for building constraint violations
     * @return true if the email is valid, false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            log.error(LogMessages.BLANKNESS_EMAIL);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.BLANKNESS_EMAIL).addConstraintViolation();
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            log.error(LogMessages.INVALID_EMAIL_FORMAT);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.INVALID_EMAIL_FORMAT).addConstraintViolation();
            return false;
        }

        if (userRepository.existsByEmail(email)) {
            log.error(LogMessages.USED_EMAIL, email);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.USED_EMAIL).addConstraintViolation();
            return false;
        }
        return true;
    }
}