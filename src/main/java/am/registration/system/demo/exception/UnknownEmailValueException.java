package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
public class UnknownEmailValueException extends RuntimeException {
    public UnknownEmailValueException(String message) {
        super(message);
    }
}
