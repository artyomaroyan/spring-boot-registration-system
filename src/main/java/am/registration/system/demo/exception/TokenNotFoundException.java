package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 03:46:28
 */
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
