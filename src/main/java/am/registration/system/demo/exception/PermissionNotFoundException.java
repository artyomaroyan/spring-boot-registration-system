package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 14.02.25
 */
public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String message) {
        super(message);
    }
}
