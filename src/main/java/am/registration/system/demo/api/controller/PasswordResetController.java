package am.registration.system.demo.api.controller;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.model.dto.PasswordResetRequest;
import am.registration.system.demo.service.user.passwordreset.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 23:51:19
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/reset")
    public ResponseEntity<ApiResponseBuilder<String>> resetPassword(@RequestBody PasswordResetRequest request) {
        var result = passwordResetService.resetPassword(request);
        return buildResponse(result);
    }

    @GetMapping("/send-email")
    public ResponseEntity<ApiResponseBuilder<String>> resetPasswordEmail(@RequestParam String email) {
        var result = passwordResetService.sendPasswordResetEmail(email);
        return buildResponse(result);
    }

    private <T> ResponseEntity<ApiResponseBuilder<T>> buildResponse(ApiResponseBuilder<T> result) {
        if (!result.success()) {
            return new ResponseEntity<>(ApiResponse.failure("Operation failed",
                    String.valueOf(HttpStatus.BAD_REQUEST)), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}