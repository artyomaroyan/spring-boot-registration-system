package am.registration.system.demo.security.token.claims;

import am.registration.system.demo.model.dto.UserPrincipal;
import am.registration.system.demo.security.token.enums.TokenPurpose;
import am.registration.system.demo.security.token.enums.TokenState;
import org.springframework.stereotype.Service;

/**
 * Factory for creating TokenClaimsDto objects based on user data and purpose.
 * *
 * Author: Artyom Aroyan
 * Date: 18.02.25
 * Time: 17:27:41
 */
@Service
public class TokenClaimsFactory {

    /**
     * Generates user token claims based on provided data.
     * @param userId the ID of the user
     * @param username the username of the user
     * @param email the email of the user
     * @param purpose the purpose of the token
     * @return the generated TokenClaimsDto
     */
    public TokenClaimsDto createUserTokenClaims(Integer userId, String username, String email, TokenPurpose purpose) {
        return TokenClaimsDto.builder()
                .userId(userId)
                .username(username)
                .userEmail(email)
                .tokenState(TokenState.PENDING.name())
                .tokenPurpose(purpose.name())
                .build();
    }

    /**
     * Generates JWT claims based on the authenticated user's principal.
     * @param user the authenticated user's principal
     * @return the generated TokenClaimsDto
     */
    public TokenClaimsDto createJwtTokenClaims(UserPrincipal user) {
        return TokenClaimsDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .userEmail(user.getEmail())
                .userState(user.getUserState())
                .tokenState(TokenState.PENDING.name())
                .build();
    }
}