package am.registration.system.demo.security.token.service;

import am.registration.system.demo.exception.UserNotFoundException;
import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.entity.UserToken;
import am.registration.system.demo.model.repository.UserTokenRepository;
import am.registration.system.demo.security.token.claims.TokenClaimsFactory;
import am.registration.system.demo.security.token.claims.TokenClaimsMapper;
import am.registration.system.demo.security.token.enums.TokenPurpose;
import am.registration.system.demo.security.token.enums.TokenState;
import am.registration.system.demo.security.token.enums.TokenType;
import am.registration.system.demo.security.token.key.provider.SigningKeyManager;
import am.registration.system.demo.service.user.management.UserManagementService;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Service for managing user tokens, including password reset and email verification.
 * <p>
 * This service is responsible for creating, saving, and invalidating user tokens,
 * as well as handling the generation and expiration of token data.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 18.02.25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenService {

    private final UserMapper userMapper;
    private final TokenGenerator tokenGenerator;
    private final TokenClaimsMapper tokenClaimsMapper;
    private final SigningKeyManager signingKeyManager;
    private final TokenClaimsFactory tokenClaimsFactory;
    private final UserTokenRepository userTokenRepository;
    private final UserManagementService userManagementService;

    /**
     * Creates a password reset token for the specified user email.
     *
     * @param email the email address of the user requesting a password reset
     * @return the created {@link UserToken} entity
     */
    public UserToken createPasswordResetToken(final String email) {
        return generateToken(email, TokenPurpose.PASSWORD_RECOVERY, TokenType.PASSWORD_RESET);
    }

    /**
     * Creates an email verification token for the specified user email.
     *
     * @param email the email address of the user to verify
     * @return the created {@link UserToken} entity
     */
    public UserToken createEmailVerificationToken(final String email) {
        return generateToken(email, TokenPurpose.ACCOUNT_VERIFICATION, TokenType.EMAIL_VERIFICATION);
    }

    /**
     * Invalidates the specified token by marking it as verified.
     *
     * @param token the {@link UserToken} to invalidate
     */
    public void invalidateToken(final UserToken token) {
        token.setTokenState(TokenState.VERIFIED);
        userTokenRepository.save(token);
    }

    /**
     * Creates a token of the specified type and purpose for the given email.
     *
     * @param email   the email address associated with the token
     * @param purpose the purpose of the token (e.g., password recovery or email verification)
     * @param type    the type of the token (e.g., PASSWORD_RESET or EMAIL_VERIFICATION)
     * @return the created {@link UserToken} entity
     */
    private UserToken generateToken(final String email, final TokenPurpose purpose, final TokenType type) {
        var user = fetchUserByEmail(email);
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + signingKeyManager.retrieveTokenExpiration(type));

        log.info(LogMessages.GENERATE_NEW_TOKEN, email);
        var token = createUserToken(user.getId(), purpose, type);

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setExpireDate(expiration);
        userToken.setTokenPurpose(purpose);
        userToken.setTokenState(TokenState.PENDING);
        userToken.setUser(user);
        return userTokenRepository.save(userToken);
    }

    /**
     * Fetches a user by email from the repository.
     *
     * @param email the email address of the user
     * @return the found {@link User} entity
     * @throws UserNotFoundException if no user is found with the given email
     */
    private User fetchUserByEmail(final String email) {
        return userMapper.mapFromResponseToEntity(userManagementService.getUserByEmail(email).data());
    }

    /**
     * Creates a user token based on the given user ID, token purpose, and token type.
     *
     * @param userId  the ID of the user for whom the token is being created
     * @param purpose the purpose of the token (e.g., authentication, password reset)
     * @param type    the type of the token (e.g., JWT, API token)
     * @return the generated token as a String
     */
    private String createUserToken(Integer userId, TokenPurpose purpose, TokenType type) {
        var user = userManagementService.getUserById(userId);
        var username = user.data().getUsername();
        var email = user.data().getEmail();
        var claimsDto = tokenClaimsFactory.createUserTokenClaims(userId, username, email, purpose);
        var claims = tokenClaimsMapper.mapUserClaims(claimsDto);
        return tokenGenerator.createToken(claims, user.data().getUsername(), type);
    }
}