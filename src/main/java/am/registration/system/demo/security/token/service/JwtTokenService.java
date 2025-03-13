package am.registration.system.demo.security.token.service;

import am.registration.system.demo.security.token.claims.TokenClaimsFactory;
import am.registration.system.demo.security.token.claims.TokenClaimsMapper;
import am.registration.system.demo.security.token.enums.TokenType;
import lombok.RequiredArgsConstructor;
import am.registration.system.demo.model.dto.UserPrincipal;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for creating JWT tokens.
 * *
 * This service leverages the TokenGenerator, TokenClaimsMapper, and
 * TokenClaimsFactory to generate a JWT token containing user-related claims.
 * *
 * Author: Artyom Aroyan
 * Date: 18.02.25
 */
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final TokenGenerator tokenGenerator;
    private final TokenClaimsMapper tokenClaimsMapper;
    private final TokenClaimsFactory tokenClaimsFactory;

    /**
     * Creates a JWT token for the given user.
     *
     * @param user the user principal containing user details and credentials
     * @return the generated JWT token as a String
     */
    public String createJwtToken(UserPrincipal user) {
        var claimsDto = tokenClaimsFactory.createJwtTokenClaims(user);
        var claims = tokenClaimsMapper.mapJwtClaims(claimsDto);
        return tokenGenerator.createToken(claims, user.getUsername(), TokenType.JSON_WEB_TOKEN);
    }
}