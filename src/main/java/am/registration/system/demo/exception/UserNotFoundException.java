package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 17.02.25
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
