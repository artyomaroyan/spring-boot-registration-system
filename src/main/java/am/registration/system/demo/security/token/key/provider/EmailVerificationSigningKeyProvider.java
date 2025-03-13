package am.registration.system.demo.security.token.key.provider;

import am.registration.system.demo.security.token.strategy.SigningKeyProviderStrategy;
import am.registration.system.demo.security.token.configuration.UserTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Provides signing keys specifically for email verification tokens.
 * <p>
 * This component implements the {@link SigningKeyProviderStrategy} interface to
 * supply the signing key required for generating and verifying email verification tokens.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 21:09:39
 */
@Component
@RequiredArgsConstructor
public class EmailVerificationSigningKeyProvider implements SigningKeyProviderStrategy {

    private final UserTokenProperties properties;

    /**
     * Retrieves the signing key used for email verification tokens.
     * <p>
     * The key is derived from the configured properties and used to sign and verify JWT tokens
     * related to email verification.
     * </p>
     *
     * @return the signing {@link Key} for email verification tokens
     */
    @Override
    public Key getSigningKey() {
        return properties.getEmailVerificationSigningKey();
    }
}