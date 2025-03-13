package am.registration.system.demo.security.token.key.provider;

import java.security.Key;

/**
 * Interface for providing cryptographic keys and token expiration information.
 * <p>
 * Implementations of this interface are responsible for supplying private and public keys
 * used for signing and verifying tokens, as well as providing the token expiration time.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 19.02.25
 * Time: 01:05:00
 */
public interface KeyProvider {
    Key getPrivateKey();
    Key getPublicKey();
    Long getExpiration();
}