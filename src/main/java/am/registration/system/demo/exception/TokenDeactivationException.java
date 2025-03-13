package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 14:24:18
 */
public class TokenDeactivationException extends RuntimeException {
    public TokenDeactivationException(String message, Throwable cause) {
        super(message, cause);
    }
}
