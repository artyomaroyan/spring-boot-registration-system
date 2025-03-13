package am.registration.system.demo.exception;

/**
 * Author: Artyom Aroyan
 * Date: 17.02.25
 */
public class KeyStoreLoadException extends RuntimeException {

    public KeyStoreLoadException(String message) {
        super(message);
    }

    public KeyStoreLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
