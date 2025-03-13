package am.registration.system.demo.security.token.key.provider;

import am.registration.system.demo.security.token.strategy.SigningKeyProviderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Implementation of the {@link SigningKeyProviderStrategy} that provides a private signing key
 * for generating JSON Web Tokens (JWT).
 * *
 * This class uses an {@link KeyProvider} to get the private key, which is required for signing JWTs.
 * The signing key is essential to ensure the integrity and authenticity of the generated tokens.
 * *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 21:06:45
 */
@Component
@RequiredArgsConstructor
public class JwtSigningKeyProvider implements SigningKeyProviderStrategy {

    private final KeyProvider jwtKeyProvider;

    @Override
    public Key getSigningKey() {
        return jwtKeyProvider.getPrivateKey();
    }
}