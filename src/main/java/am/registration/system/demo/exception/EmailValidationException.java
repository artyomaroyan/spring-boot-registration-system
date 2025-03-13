package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 23:34:00
 */
public class EmailValidationException extends RuntimeException {
    public EmailValidationException(String message) {
        super(message);
    }
}