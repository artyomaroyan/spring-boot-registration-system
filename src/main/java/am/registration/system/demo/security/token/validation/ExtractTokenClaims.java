package am.registration.system.demo.security.token.validation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Author: Artyom Aroyan
 * Date: 13.03.25
 * Time: 22:16:58
 */
@Component
class ExtractTokenClaims {

    protected Claims extractClaimsFormToken(final String token, final Key signingKey) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
