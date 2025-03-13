package am.registration.system.demo.security.token.key.loader;

import am.registration.system.demo.exception.KeyStoreLoadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * Utility class for loading EC (Elliptic Curve) keys and keystores.
 * <p>
 * This class provides methods to load a key, as well as extract private and public keys
 * from it. The keys are expected to be of the Elliptic Curve (EC) type, using the PKCS12 format.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 16.02.25
 */
@Slf4j
@Component
public class EcKeyStoreManager {

    /**
     * Loads a key from the specified file path.
     * <p>
     * The key is expected to be in PKCS12 format. If loading fails, a custom
     * {@link KeyStoreLoadException} is thrown.
     * </p>
     *
     * @param path     the file path of the key
     * @param password the password to unlock the key
     * @return the loaded {@link KeyStore} instance
     * @throws KeyStoreLoadException if the key could not be loaded
     */
    public KeyStore loadKeyStore(String path, char[] password) {
        try (FileInputStream stream = new FileInputStream(path)) {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(stream, password);
            return keyStore;
        } catch (Exception ex) {
            log.error("Keystore loading failed for path: {}", path, ex);
            throw new KeyStoreLoadException("Failed to load key from: " + path, ex);
        }
    }

    /**
     * Loads a private key from the specified key.
     * <p>
     * The private key is expected to be an Elliptic Curve (EC) private key. If the key
     * is not of the expected type or loading fails, a {@link KeyStoreLoadException} is thrown.
     * </p>
     *
     * @param keyStore the loaded key from which to retrieve the private key
     * @param alias    the alias of the private key entry
     * @param password the password to unlock the private key
     * @return the loaded {@link PrivateKey} instance
     * @throws KeyStoreLoadException if the private key could not be loaded or is of the wrong type
     */
    public PrivateKey loadPrivateKey(KeyStore keyStore, String alias, char[] password) {
        try {
            Key key = keyStore.getKey(alias, password);
            if (!(key instanceof ECPrivateKey)) {
                throw new KeyStoreLoadException("Key is not a EC private key: " + key.getClass().getName());
            }
            return (ECPrivateKey) key;
        } catch (Exception ex) {
            throw new KeyStoreLoadException("Failed to load private key from: " + alias, ex);
        }
    }

    /**
     * Loads a public key from the specified key.
     * <p>
     * The public key is expected to be an Elliptic Curve (EC) public key. If the key
     * is not of the expected type or loading fails, a {@link KeyStoreLoadException} is thrown.
     * </p>
     *
     * @param keyStore the loaded key from which to retrieve the public key
     * @param alias    the alias of the public key entry
     * @return the loaded {@link PublicKey} instance
     * @throws KeyStoreLoadException if the public key could not be loaded or is of the wrong type
     */
    public PublicKey loadPublicKey(KeyStore keyStore, String alias) {
        try {
            Certificate certificate = keyStore.getCertificate(alias);
            PublicKey publicKey = certificate.getPublicKey();
            if (!(publicKey instanceof ECPublicKey)) {
                throw new KeyStoreLoadException("Key is not a EC public key: " + publicKey.getClass().getName() );
            }
            return certificate.getPublicKey();
        } catch (Exception ex) {
            throw new KeyStoreLoadException("Failed to load public key from: " + alias, ex);
        }
    }
}