package am.registration.system.demo.service.user.validation.validator;

import am.registration.system.demo.service.user.validation.annotation.ValidPassword;
import am.registration.system.demo.util.LogMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validates the password format and ensures compliance with security requirements.
 * Passwords must be between 8 and 45 characters long and contain at least one uppercase letter,
 * one lowercase letter, one digit, and one special character from the set [@$!%*?&., ].
 * Logs appropriate error messages when validation fails.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 00:54:48
 */
@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&.,])[A-Za-z\\d@$!%*?&.,]{8,45}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Logger log = LoggerFactory.getLogger(PasswordValidator.class);

    /**
     * Validates the given password according to security criteria.
     *
     * @param password the password to validatePasswordResetRequest
     * @param context  the validation context for building constraint violations
     * @return true if the password is valid, false otherwise
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.trim().isEmpty()) {
            errors.add(LogMessages.BLANKNESS_PASSWORD);
        }
        if (password != null && !PASSWORD_PATTERN.matcher(password).matches()) {
            errors.add(LogMessages.INVALID_PASSWORD_FORMAT);
        }
        if (!errors.isEmpty()) {
            errors.forEach(error -> {
                log.error(error);
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
            });
            return false;
        }
        return true;
    }
}