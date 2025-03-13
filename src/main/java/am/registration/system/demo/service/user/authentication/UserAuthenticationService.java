package am.registration.system.demo.service.user.authentication;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.dto.AuthenticationRequest;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.security.token.service.JwtTokenService;
import am.registration.system.demo.service.user.management.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authenticating users and generating JWT tokens.
 * Uses Spring Security's AuthenticationManager for authentication
 * and the UserManagementService for retrieving user details.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 16:10:55
 */
@Service
@RequiredArgsConstructor
class UserAuthenticationService {

    private final UserMapper userMapper;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserManagementService userManagementService;

    /**
     * Authenticates the user using the provided credentials and generates a JWT token.
     *
     * @param request the authentication request containing username and password
     * @return ApiResponseBuilder<String> containing the JWT token prefixed with "Bearer"
     * @throws AuthenticationException if authentication fails
     */
    protected ApiResponseBuilder<String> authenticate(final AuthenticationRequest request) {
        authenticateUser(request);
        var user = fetchUserByUsername(request.username());
        var userPrincipal = userMapper.mapFromEntityToPrincipal(user);
        var token = jwtTokenService.createJwtToken(userPrincipal);
        return ApiResponse.success(token, "Bearer ");
    }

    /**
     * Retrieves the user details by username from the UserManagementService.
     * Maps the user response to a User entity.
     *
     * @param username the username to look up
     * @return the User entity corresponding to the provided username
     * @throws UsernameNotFoundException if the user is not found
     */
    private User fetchUserByUsername(final String username) {
        return userMapper.mapFromResponseToEntity(userManagementService.getUserByUsername(username).data());
    }

    /**
     * Authenticates the user's credentials using Spring Security.
     *
     * @param request the authentication request containing username and password
     * @throws AuthenticationException if authentication fails
     */
    private void authenticateUser(final AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    }
}