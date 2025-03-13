package am.registration.system.demo.security.token.strategy;

import java.security.Key;

/**
 * strategy interface for providing signing keys.
 * <p>
 * Implementations of this interface are responsible for supplying the signing key
 * used to generate digital signatures for various token types.
 * This abstraction allows flexibility in choosing different signing key providers.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 21:05:48
 */
public interface SigningKeyProviderStrategy {
    Key getSigningKey();
}