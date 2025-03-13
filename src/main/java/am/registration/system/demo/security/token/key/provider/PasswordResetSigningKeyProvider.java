package am.registration.system.demo.security.token.key.provider;

import am.registration.system.demo.security.token.strategy.SigningKeyProviderStrategy;
import am.registration.system.demo.security.token.configuration.UserTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Signing key provider for password reset tokens.
 * <p>
 * This component implements the {@link SigningKeyProviderStrategy} interface to provide a signing key
 * specifically for password reset tokens.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 21:08:34
 */
@Component
@RequiredArgsConstructor
public class PasswordResetSigningKeyProvider implements SigningKeyProviderStrategy {

    private final UserTokenProperties properties;

    /**
     * Retrieves the signing key used for generating password reset tokens.
     * <p>
     * The signing key is obtained from the {@link UserTokenProperties} configuration.
     * </p>
     *
     * @return the signing key for password reset tokens
     */
    @Override
    public Key getSigningKey() {
        return properties.getPasswordResetSigningKey();
    }
}