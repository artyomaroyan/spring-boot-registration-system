package am.registration.system.demo.service.user.authentication;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.configuration.application.LinkConfiguration;
import am.registration.system.demo.email.EmailSender;
import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.dto.UserResponse;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.security.token.service.JwtTokenService;
import am.registration.system.demo.security.token.service.UserTokenService;
import am.registration.system.demo.service.user.validation.UserRequestValidator;
import am.registration.system.demo.util.ExceptionMessages;
import am.registration.system.demo.util.LogMessages;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for handling user registration processes, including
 * - Validating registration requests.
 * - Creating and saving new users.
 * - Generating verification tokens.
 * - Sending verification emails.
 * - Building successful registration responses.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 01:01:48
 */
@Slf4j
@Service
@RequiredArgsConstructor
class UserRegistrationService {

    private final UserMapper userMapper;
    private final EmailSender emailSender;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final UserTokenService userTokenService;
    private final UserRequestValidator userRequestValidator;

    /**
     * Handles user registration, including validation, saving the user,
     * generating a verification token, sending a verification email,
     * and building a successful response.
     *
     * @param request the user registration request
     * @return ApiResponseBuilder<UserResponse> the response containing user data and a success message
     * @throws ValidationException if the registration request is invalid
     */
    protected ApiResponseBuilder<UserResponse> register(final UserRequest request) {
        validateUserRegistrationRequest(request);
        var user = createAndSaveUser(request);
        var token = generateVerificationToken(request.getEmail());
        sendVerificationEmail(user, token);
        var userPrincipal = userMapper.mapFromEntityToPrincipal(user);
        var testToken = jwtTokenService.createJwtToken(userPrincipal);
        log.info("JWT token: {}", testToken);
        return buildSuccessResponse(user);
    }

    /**
     * Validates the user registration request.
     *
     * @param request the user registration request to validatePasswordResetRequest
     * @throws ValidationException if any validation errors are found
     */
    private void validateUserRegistrationRequest(final UserRequest request) {
        List<String> errors = userRequestValidator.validateRequest(request);
        if (!errors.isEmpty()) {
            log.error(LogMessages.REGISTRATION_FAILED, errors);
            throw new ValidationException(ExceptionMessages.REGISTRATION_FAILED + errors);
        }
    }

    /**
     * Creates a new user entity from the registration request and saves it to the database.
     *
     * @param request the user registration request containing user data
     * @return the saved User entity
     */
    private User createAndSaveUser(final UserRequest request) {
        var user = userMapper.mapFromRequestToEntity(request);
        return userRepository.save(user);
    }

    /**
     * Generates an email verification token for the given email address.
     *
     * @param email the email address to generate a verification token for
     * @return the generated token as a String
     */
    private String generateVerificationToken(final String email) {
        var token = userTokenService.createEmailVerificationToken(email);
        return token.getToken();
    }

    /**
     * Sends a verification email to the registered user.
     *
     * @param user  the registered user to send the verification email to
     * @param token the verification token to include in the email
     */
    private void sendVerificationEmail(final User user, final String token) {
        var verificationLink = LinkConfiguration.emailVerificationLink(token);
        var subject = LinkConfiguration.VERIFICATION_EMAIL_SUBJECT;
        emailSender.send(user.getEmail(), subject, verificationLink);
    }

    /**
     * Builds a successful registration response containing the user data.
     *
     * @param user the registered user whose data will be included in the response
     * @return ApiResponseBuilder<UserResponse> the response with user details and a success message
     */
    private ApiResponseBuilder<UserResponse> buildSuccessResponse(final User user) {
        var response = userMapper.mapFromEntityToResponse(user);
        return ApiResponse.success(response, LogMessages.SUCCESSFULLY_REGISTERED);
    }
}