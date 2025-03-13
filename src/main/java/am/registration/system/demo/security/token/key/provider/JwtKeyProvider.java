package am.registration.system.demo.security.token.key.provider;

import am.registration.system.demo.security.token.configuration.JwtTokenProperties;
import am.registration.system.demo.security.token.key.loader.EcKeyStoreManager;
import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Provides private and public keys for JWT signing and verification.
 * Loads keys from a key and manages JWT expiration time.
 * *
 * This class uses EC (Elliptic Curve) keys for signing and verification,
 * leveraging a configured key. The expiration time is retrieved from
 * application properties and converted to milliseconds.
 * *
 * * Dependencies:
 * - JwtTokenProperties: Contains configuration properties related to JWT.
 * - EcKeyStoreManager: Utility to load keys from a key.
 * *
 * Author: Artyom Aroyan
 * Date: 17.02.25
 */
@Getter
@Component
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtKeyProvider implements KeyProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final Long expiration;

    /**
     * Constructs a JwtKeyProvider by loading private and public keys from the configured key.
     *
     * @param properties    the JWT properties containing key configuration
     * @param ecKeyStoreUtil the utility to load keys from the key
     */
    public JwtKeyProvider(JwtTokenProperties properties, EcKeyStoreManager ecKeyStoreUtil) {
        KeyStore keyStore = ecKeyStoreUtil.loadKeyStore(properties.getKeystorePath(), properties.getKeystorePassword().toCharArray());
        this.privateKey = ecKeyStoreUtil.loadPrivateKey(keyStore, properties.getKeystoreAlias(), properties.getKeystorePassword().toCharArray());
        this.publicKey = ecKeyStoreUtil.loadPublicKey(keyStore, properties.getKeystoreAlias());
        this.expiration = properties.getExpiration();
    }

    @Override
    public Key getPrivateKey() {
        return privateKey;
    }

    @Override
    public Key getPublicKey() {
        return publicKey;
    }

    @Override
    public Long getExpiration() {
        return expiration * 60 * 1000;
    }
}