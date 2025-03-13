package am.registration.system.demo.security.token.configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Manages properties and utility methods related to user token management,
 * including password reset and email verification tokens.
 * <p>
 * This class is responsible for loading token properties from the application
 * configuration and generating signing keys for each token type.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 16.02.25
 */
@Slf4j
@Component
public class UserTokenProperties {

    @Value("${application.security.passwordResetToken.secret}")
    private String passwordResetTokenSecret;
    @Value("${application.security.passwordResetToken.expiration}")
    private Long passwordResetTokenExpiration;
    @Value("${application.security.emailVerificationToken.secret}")
    private String emailVerificationTokenSecret;
    @Value("${application.security.emailVerificationToken.expiration}")
    private Long emailVerificationTokenExpiration;

    public Long getPasswordResetTokenExpirationInMillis() {
        return passwordResetTokenExpiration * 60 * 1000;
    }

    public Long getEmailVerificationTokenExpirationInMillis() {
        return emailVerificationTokenExpiration * 60 * 1000;
    }

    public Key getPasswordResetSigningKey() {
        return getSigningKey(passwordResetTokenSecret);
    }

    public Key getEmailVerificationSigningKey() {
        return getSigningKey(emailVerificationTokenSecret);
    }

    private Key getSigningKey(String hexSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(hexSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}