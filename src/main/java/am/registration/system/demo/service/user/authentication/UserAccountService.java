package am.registration.system.demo.service.user.authentication;

import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.model.dto.AuthenticationRequest;
import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for managing user account operations such as registration, login, and email verification.
 * Delegates the processing to specialized services for registration, authentication, and verification.
 * *
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 01:49:47
 */
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRegistrationService userRegistrationService;
    private final UserAuthenticationService userAuthenticationService;
    private final UserActivationHandler userActivationHandler;

    /**
     * Registers a new user by delegating to the UserRegistrationService service.
     *
     * @param userRequest the user registration request containing necessary user details
     * @return ApiResponseBuilder<UserResponse> containing the registration response
     */
    @Transactional
    public ApiResponseBuilder<UserResponse> register(final UserRequest userRequest) {
        return userRegistrationService.register(userRequest);
    }

    /**
     * Authenticates the user by delegating to the UserAuthenticationService service.
     *
     * @param request the authentication request containing username and password
     * @return ApiResponseBuilder<String> containing the authentication token upon success
     */
    public ApiResponseBuilder<String> login(final AuthenticationRequest request) {
        return userAuthenticationService.authenticate(request);
    }

    /**
     * Verifies the user's email by delegating to the UserActivationHandler service.
     *
     * @param email the email to be verified
     * @return ApiResponseBuilder<String> containing the verification response
     */
    @Transactional
    public ApiResponseBuilder<String> verifyUserEmail(final String email) {
        return userActivationHandler.activateUserAccount(email);
    }
}