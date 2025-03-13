package am.registration.system.demo.security.token.claims;

import am.registration.system.demo.model.enums.UserState;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * DTO representing the claims associated with a token.
 * Used for both JWT and user-specific token claims.
 * *
 * Author: Artyom Aroyan
 * Date: 17.02.25
 */
@Builder
public record TokenClaimsDto(Integer userId, String username, String userEmail, String tokenState, String tokenPurpose,
                      Set<String> roles, Collection<? extends GrantedAuthority> authorities, UserState userState) {
}
