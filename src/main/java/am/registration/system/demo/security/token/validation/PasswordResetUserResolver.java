package am.registration.system.demo.security.token.validation;

import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.security.token.configuration.UserTokenProperties;
import am.registration.system.demo.service.user.management.UserManagementService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Manages password reset tokens by extracting user information from the token.
 * <p>
 * This component is responsible for parsing JWT password reset tokens and retrieving the associated user.
 * </p>
 * Author: Artyom Aroyan
 * Date: 13.03.25
 * Time: 22:27:32
 */
@Component
@RequiredArgsConstructor
public class PasswordResetUserResolver {

    private final UserMapper userMapper;
    private final ExtractTokenClaims extractTokenClaims;
    private final UserTokenProperties userTokenProperties;
    private final UserManagementService userManagementService;

    /**
     * Extracts the user associated with the given password reset token.
     * <p>
     * This method parses the JWT token to extract claims, including the username, and then retrieves the user
     * from the database. If the username is not found, an exception is thrown.
     * </p>
     *
     * @param token the JWT password reset token
     * @return the {@link User} associated with the token
     * @throws UsernameNotFoundException if the user is not found
     */
    public User extractUserFromToken(final String token) {
        Claims claims = extractTokenClaims.extractClaimsFormToken(token, userTokenProperties.getPasswordResetSigningKey());
        final String username = claims.getSubject();
        return userMapper.mapFromResponseToEntity(userManagementService.getUserByUsername(username).data());
    }
}
