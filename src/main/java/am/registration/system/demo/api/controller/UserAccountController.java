package am.registration.system.demo.api.controller;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.model.dto.AuthenticationRequest;
import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.dto.UserResponse;
import am.registration.system.demo.service.user.authentication.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 23:12:21
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseBuilder<UserResponse>> register(@RequestBody @Valid UserRequest request) {
        var result = userAccountService.register(request);
        return buildResponse(result);
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ApiResponseBuilder<String>> verifyEmail(@PathVariable String token) {
        var result = userAccountService.verifyUserEmail(token);
        return buildResponse(result);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseBuilder<String>> login(@RequestBody @Valid AuthenticationRequest request) {
        var result = userAccountService.login(request);
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