package am.registration.system.demo.service.user.validation;

import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.service.user.validation.validator.EmailValidator;
import am.registration.system.demo.service.user.validation.validator.PasswordValidator;
import am.registration.system.demo.service.user.validation.validator.PhoneValidator;
import am.registration.system.demo.service.user.validation.validator.UsernameValidator;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates user registration and update requests by checking the validity of email, phone number, password, and username.
 * Uses dedicated validation components for each field and aggregate validation errors into a list.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 01:22:18
 */
@Component
@RequiredArgsConstructor
public class UserRequestValidator {

    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;
    private final PasswordValidator passwordValidator;
    private final UsernameValidator usernameValidator;

    /**
     * Validates a user request object, performing checks on email, phone number, password, and username.
     * Aggregates any validation errors found into a list of error messages.
     *
     * @param request the UserRequest object containing the user details to validatePasswordResetRequest
     * @return a list of validation error messages, or an empty list if no errors are found
     */
    public List<String> validateRequest(UserRequest request) {
        List<String> errors = new ArrayList<>();
        // Validate email and add an error message if invalid
        if (!emailValidator.isValid(request.getEmail(), null)) {
            errors.add(LogMessages.INVALID_EMAIL);
        }
        // Validate the phone number and add an error message if invalid
        if (!phoneValidator.isValid(request.getPhone(), null)) {
            errors.add(LogMessages.INVALID_PHONE);
        }
        // Validate the password and add an error message if invalid
        if (!passwordValidator.isValid(request.getPassword(), null)) {
            errors.add(LogMessages.INVALID_PASSWORD);
        }
        // Validate the username and add an error message if invalid
        if (!usernameValidator.isValid(request.getUsername(), null)) {
            errors.add(LogMessages.INVALID_USERNAME);
        }
        // Return the list of accumulated errors, or an empty list if all validations passed
        return errors;
    }
}