package am.registration.system.demo.security.token.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Configuration class for loading the KeyStore required for JWT signing and verification.
 * <p>
 * This configuration loads the PKCS12 key using the file path and password specified
 * in the {@link JwtTokenProperties}. The key is then registered as a Spring bean.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 16.02.25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
class JwtKeyStoreConfiguration {

    private final JwtTokenProperties jwtTokenProperties;

    /**
     * Creates and loads the KeyStore bean using the specified configuration properties.
     * <p>
     * The key is loaded from the file path specified in the application properties
     * and is protected by a password. The loaded key is of type "PKCS12".
     * </p>
     *
     * @return the loaded {@link KeyStore} instance
     * @throws CertificateException        if any of the certificates in the key could not be loaded
     * @throws IOException                 if there is an I/O or format problem with the key data
     * @throws NoSuchAlgorithmException    if the algorithm used to check the integrity of the key cannot be found
     * @throws KeyStoreException           if the key type is not available
     */
    @Bean
    public KeyStore createKeyStore() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream inputStream = new FileInputStream(jwtTokenProperties.getKeystorePath())) {
            keyStore.load(inputStream, jwtTokenProperties.getKeystorePassword().toCharArray());
        }
        return keyStore;
    }
}