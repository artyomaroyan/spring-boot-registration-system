package am.registration.system.demo.security.token.claims;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Mapper for converting TokenClaimsDto objects to Map representations.
 * Provides methods to map both user-specific claims and JWT-specific claims.
 * *
 * Author: Artyom Aroyan
 * Date: 18.02.25
 * Time: 17:05:19
 */
@Component
public class TokenClaimsMapper {

    /**
     * Maps user token claims to a Map representation.
     * @param claims the TokenClaimsDto containing user claims
     * @return a Map of user claim keys and their corresponding values
     */
    public Map<String, Object> mapUserClaims(TokenClaimsDto claims) {
        return convertClaimsToMap(claims, Map.of(
                TokenClaimConstants.USER_ID, TokenClaimsDto::userId,
                TokenClaimConstants.USERNAME, TokenClaimsDto::username,
                TokenClaimConstants.EMAIL, TokenClaimsDto::userEmail,
                TokenClaimConstants.TOKEN_PURPOSE, TokenClaimsDto::tokenPurpose,
                TokenClaimConstants.TOKEN_STATE, TokenClaimsDto::tokenState
        ));
    }

    /**
     * Maps JWT claims to a Map representation.
     * @param claims the TokenClaimsDto containing JWT claims
     * @return a Map of JWT claim keys and their corresponding values
     */
    public Map<String, Object> mapJwtClaims(TokenClaimsDto claims) {
        return convertClaimsToMap(claims, Map.of(
                TokenClaimConstants.USER_ID, TokenClaimsDto::userId,
                TokenClaimConstants.USERNAME, TokenClaimsDto::username,
                TokenClaimConstants.USER_ROLES, TokenClaimsDto::roles,
                TokenClaimConstants.AUTHORITIES, TokenClaimsDto::authorities,
                TokenClaimConstants.USER_STATE, TokenClaimsDto::userState
        ));
    }

    /**
     * Converts claims to a Map using the specified extractor functions.
     * @param claims the claims object to convert
     * @param claimsExtractor a map of claim keys and functions to extract the values
     * @param <T> the type of the claims object
     * @return a Map of claim keys and their corresponding values
     */
    private <T> Map<String, Object> convertClaimsToMap(T claims, Map<String, Function<T, Object>> claimsExtractor) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsExtractor.forEach((key, extractor) ->
                Optional.ofNullable(extractor.apply(claims))
                        .ifPresent(value -> claimsMap.put(key, value)));
        return claimsMap;
    }
}