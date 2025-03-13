package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 15.02.25
 */
public class InvalidEncodedPasswordException extends RuntimeException {
    public InvalidEncodedPasswordException(String message) {
        super(message);
    }
}
