package am.registration.system.demo.security.password;

import am.registration.system.demo.exception.InvalidEncodedPasswordException;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * A password encoder implementation that uses the Argon2 hashing algorithm to securely store passwords.
 * This class implements the {@link PasswordEncoder} interface and provides both encoding and verification methods.
 * *
 * <p>Argon2 is a memory-hard hashing function designed to resist brute-force attacks and is highly configurable
 * with parameters for memory usage, iterations, and parallelism.</p>
 * *
 * * Usage:
 * - Call {@code encode()} to generate a hashed password.
 * - Call {@code matches()} to verify the raw password against a stored hash.
 * *
 * Author: Artyom Aroyan
 * Date: 15.02.25
 */
@Component
@RequiredArgsConstructor
public class Argon2Hashing implements PasswordEncoder {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final Argon2Properties argon2Properties;

    /**
     * Encodes the raw password using the Argon2 hashing algorithm.
     *
     * @param rawPassword the raw password to encode
     * @return the encoded password in a Base64 format: salt:secret:hashedPassword
     */
    @Override
    public String encode(CharSequence rawPassword) {
        byte[] salt = generateSalt();
        byte[] secret = getSecret();
        byte[] password = hashPassword(rawPassword.toString(), salt, secret);

        Base64.Encoder encoder = Base64.getEncoder();
        return String.join(":",
                encoder.encodeToString(salt),
                encoder.encodeToString(secret),
                encoder.encodeToString(password));
    }

    /**
     * Verifies whether the raw password matches the encoded password.
     *
     * @param rawPassword    the raw password to verify
     * @param encodedPassword the previously encoded password to match against
     * @return true if the raw password matches the encoded password, false otherwise
     * @throws InvalidEncodedPasswordException if the encoded password format is invalid
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split(":");
        if (parts.length != 3) {
            throw new InvalidEncodedPasswordException("Encoded password format is invalid.");
        }

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] salt = decoder.decode(parts[0]);
        byte[] secret = decoder.decode(parts[1]);
        byte[] expected = decoder.decode(parts[2]);
        byte[] actual = hashPassword(rawPassword.toString(), salt, secret);
        return Arrays.equals(actual, expected);
    }

    /**
     * Hashes the password using Argon2 with the specified salt and secret.
     *
     * @param password the raw password to hash
     * @param salt     the salt to use for hashing
     * @param secret   the secret key to enhance security
     * @return the hashed password as a byte array
     */
    private byte[] hashPassword(String password, byte[] salt, byte[] secret) {
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        Argon2Parameters.Builder builder = new  Argon2Parameters.Builder()
                .withSalt(salt)
                .withSecret(secret)
                .withParallelism(argon2Properties.getParallelism())
                .withMemoryAsKB(argon2Properties.getMemory())
                .withIterations(argon2Properties.getIterations());

        generator.init(builder.build());
        byte[] hash = new byte[argon2Properties.getHashLength()];
        generator.generateBytes(password.toCharArray(), hash);
        return hash;
    }

    /**
     * Generates a random salt for use in password hashing.
     *
     * @return a byte array representing the salt
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[argon2Properties.getSaltLength()];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Retrieves the secret key to use in hashing, encoded as UTF-8 bytes.
     *
     * @return the secret key as a byte array
     */
    private byte[] getSecret() {
        return argon2Properties.getSecret().getBytes(StandardCharsets.UTF_8);
    }
}