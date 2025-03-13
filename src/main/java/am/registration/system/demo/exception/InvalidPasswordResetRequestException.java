package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 23.02.25
 * Time: 23:59:50
 */
public class InvalidPasswordResetRequestException extends RuntimeException {
    public InvalidPasswordResetRequestException(String message) {
        super(message);
    }
}
