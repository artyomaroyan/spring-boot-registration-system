package am.registration.system.demo.service.user.validation.validator;

import am.registration.system.demo.service.user.validation.annotation.ValidPhone;
import am.registration.system.demo.util.LogMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Validates phone numbers to ensure they meet specified length and format requirements.
 * Phone numbers must contain only digits and may optionally start with a plus sign.
 * The length must be between 9 and 12 characters.
 * Logs appropriate error messages when validation fails.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 00:39:26
 */
@Component
@RequiredArgsConstructor
public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static final Logger log = LoggerFactory.getLogger(PhoneValidator.class);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{9,12}$");

    /**
     * Validates the given phone number according to length and format criteria.
     *
     * @param number  the phone number to validatePasswordResetRequest
     * @param context the validation context for building constraint violations
     * @return true if the phone number is valid, false otherwise
     */
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (number == null || number.trim().isEmpty()) {
            log.error(LogMessages.BLANKNESS_PHONE);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.BLANKNESS_PHONE).addConstraintViolation();
            return false;
        }

        if (number.length() < 9 || number.length() > 12) {
            log.error(LogMessages.INVALID_NUMBER_LENGTH);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.INVALID_NUMBER_LENGTH).addConstraintViolation();
            return false;
        }

        if (!PHONE_PATTERN.matcher(number).matches()) {
            log.error(LogMessages.INVALID_CHARACTERS, number);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(LogMessages.INVALID_CHARACTERS).addConstraintViolation();
            return false;
        }
        return true;
    }
}