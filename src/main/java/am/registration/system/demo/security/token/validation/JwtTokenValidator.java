package am.registration.system.demo.security.token.validation;

import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.security.token.key.provider.JwtKeyProvider;
import am.registration.system.demo.service.user.management.UserManagementService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Implementation of the IJwtTokenValidator interface for validating JWT tokens.
 * This class is responsible for verifying token integrity, extracting claims,
 * and ensuring the token has not expired. It uses a public key for signature validation.
 * *
 * * Dependencies:
 * - UserMapper: Maps user-related data between different layers.
 * - JwtKeyProvider: Provides the public key used for JWT signature validation.
 * - UserManagementService: Retrieves user information from the data source.
 * *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 20:41:45
 */
@Component
@RequiredArgsConstructor
public class JwtTokenValidator implements IJwtTokenValidator {

    private final UserMapper userMapper;
    private final JwtKeyProvider jwtKeyProvider;
    private final ExtractTokenClaims extractTokenClaims;
    private final UserManagementService userManagementService;

    /**
     * Validates the given JWT token by checking its integrity, comparing the embedded username
     * with the expected one, and verifying that the token has not expired.
     *
     * @param token    the JWT token to validatePasswordResetRequest
     * @param username the expected username to be matched against the token's claims
     * @return true if the token is valid, the username matches, and the token is not expired; false otherwise
     */
    @Override
    public boolean validateToken(final String token, final String username) {
        final User user = extractUserFromToken(token);
        return user != null && user.getUsername().equals(username) && !isTokenExpired(token);
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username embedded in the token
     */
    public String extractUsername(final String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token to be validated
     * @return true if the token is expired; false otherwise
     */
    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token from which to extract the expiration date
     * @return the expiration date of the token
     */
    private Date extractExpiration(final String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Extracts all claims from the JWT token.
     * Uses the public key provided by the JwtKeyProvider to validatePasswordResetRequest the signature.
     *
     * @param token the JWT token to be parsed
     * @return the Claims object containing all the token's claims
     */
    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKeyProvider.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts a User entity from the given JWT token by parsing its claims.
     * Uses the username embedded in the token to retrieve the corresponding user entity.
     *
     * @param token the JWT token containing user information
     * @return the User entity extracted from the token or null if not found
     */
    private User extractUserFromToken(final String token) {
        Claims claims = extractTokenClaims.extractClaimsFormToken(token, jwtKeyProvider.getPublicKey());
        final String username = claims.getSubject();
        return userMapper.mapFromResponseToEntity(userManagementService.getUserByUsername(username).data());
    }
}