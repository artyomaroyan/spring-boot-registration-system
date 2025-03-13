package am.registration.system.demo.api.controller;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.dto.UserResponse;
import am.registration.system.demo.service.user.management.UserManagementService;
import am.registration.system.demo.service.user.management.UserUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 23:24:13
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/management")
public class UserManagementController {

    private final UserUpdateService userUpdateService;
    private final UserManagementService userManagementService;

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('USER') && hasPermission(#id, 'USER', ' UPDATE_PROFILE,')")
    public ResponseEntity<ApiResponseBuilder<UserResponse>> updateUser(@PathVariable Integer id, @RequestBody @Valid UserRequest request) {
        var result = userUpdateService.update(id, request);
        return buildResponse(result);
    }

    @GetMapping("/get-all-users")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseBuilder<List<UserResponse>>> getAllUsers() {
        var result = userManagementService.getAllUsers();
        return buildResponse(result);
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseBuilder<UserResponse>> getUserById(@PathVariable Integer id) {
        var result = userManagementService.getUserById(id);
        return buildResponse(result);
    }

    @GetMapping("get-by-username/{username}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseBuilder<UserResponse>> getUserByUsername(@PathVariable String username) {
        var result = userManagementService.getUserByUsername(username);
        return buildResponse(result);
    }

    @GetMapping("/get-by-email")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponseBuilder<UserResponse>> getUserByEmail(@RequestParam String email) {
        var result = userManagementService.getUserByEmail(email);
        return buildResponse(result);
    }

    @GetMapping("/get-by-name/{name}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseBuilder<UserResponse>> getUserByName(@PathVariable String name) {
        var result = userManagementService.getUserByName(name);
        return buildResponse(result);
    }

    @DeleteMapping("/delete-by-id/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN' && hasPermission('UPDATE_PROFILE', 'MANAGE_USERS'))")
    public ResponseEntity<ApiResponseBuilder<String>> deleteUser(@PathVariable Integer id) {
        var result = userManagementService.deleteUserById(id);
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