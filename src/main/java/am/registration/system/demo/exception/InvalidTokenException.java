package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 14:01:19
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
