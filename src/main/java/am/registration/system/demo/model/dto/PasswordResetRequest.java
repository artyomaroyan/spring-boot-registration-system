package am.registration.system.demo.model.dto;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 22:51:10
 */
public record PasswordResetRequest(String token, String password) {
}
