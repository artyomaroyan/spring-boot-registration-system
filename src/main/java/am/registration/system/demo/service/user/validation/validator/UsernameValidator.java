package am.registration.system.demo.service.user.validation.validator;

import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.service.user.validation.annotation.ValidUsername;
import am.registration.system.demo.util.LogMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Validates usernames to ensure they meet specified length and uniqueness requirements.
 * Usernames must have a length between 5 and 20 characters and must not already exist in the database.
 * Logs appropriate error messages when validation fails.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 00:12:34
 */
@Component
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final Logger log = LoggerFactory.getLogger(UsernameValidator.class);
    private final UserRepository userRepository;

    /**
     * Validates the given username according to length and uniqueness criteria.
     *
     * @param username the username to validatePasswordResetRequest
     * @param context  the validation context for building constraint violations
     * @return true if the username is valid, false otherwise
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            log.error(LogMessages.BLANKNESS_USERNAME);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.BLANKNESS_USERNAME).addConstraintViolation();
            return false;
        }

        if (username.length() < 5 || username.length() > 20) {
            log.error(LogMessages.INVALID_USERNAME_LENGTH);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.INVALID_USERNAME_LENGTH).addConstraintViolation();
            return false;
        }

        if (userRepository.existsByUsername(username)) {
            log.error(LogMessages.USED_USERNAME, username);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.USED_USERNAME).addConstraintViolation();
            return false;
        }
        return true;
    }
}