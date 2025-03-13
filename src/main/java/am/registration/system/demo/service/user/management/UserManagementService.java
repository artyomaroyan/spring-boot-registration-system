package am.registration.system.demo.service.user.management;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.dto.UserResponse;
import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing user-related operations, including retrieval and deletion.
 * Provides methods to fetch users by various criteria and delete users by ID.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 15:52:14
 */
@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Checks if the given user ID and username correspond to the current user.
     *
     * @param userId   the ID of the user
     * @param username the username to verify
     * @return true if the current user matches the given ID and username, false otherwise
     */
    public boolean isCurrentUser(final Integer userId, final String username) {
        // ToDo: fix this method to return current logged in user data, it is desirable to return data from JWT token
        ApiResponseBuilder<UserResponse> user = getUserById(userId);
        return user != null && (user.data().getUsername().equals(username));
    }

    /**
     * Retrieves all registered users.
     *
     * @return ApiResponseBuilder containing a list of user responses
     */
    public ApiResponseBuilder<List<UserResponse>> getAllUsers() {
        var users = userRepository.findAll();
        if (users.isEmpty()) {
            return ApiResponse.failure(LogMessages.NO_USER_FOUND, String.valueOf(HttpStatus.NOT_FOUND));
        }
        var response = userMapper.mapFromEntityListToResponseList(users);
        return ApiResponse.success(response, LogMessages.SUCCESS_RESPONSE);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return ApiResponseBuilder containing the user response if found
     */
    public ApiResponseBuilder<UserResponse> getUserById(final Integer id) {
        return userRepository.findById(Long.valueOf(id))
                .map(userMapper::mapFromEntityToResponse)
                .map(response -> ApiResponse.success(response, LogMessages.SUCCESS_RESPONSE))
                .orElseGet(() -> ApiResponse.failure(LogMessages.NO_USER_FOUND, String.valueOf(HttpStatus.NOT_FOUND)));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return ApiResponseBuilder containing the user response if found
     */
    public ApiResponseBuilder<UserResponse> getUserByUsername(final String username) {
       return userRepository.findUserByUsername(username)
               .map(userMapper::mapFromEntityToResponse)
               .map(response -> ApiResponse.success(response, LogMessages.SUCCESS_RESPONSE))
               .orElseGet(() -> ApiResponse.failure(LogMessages.NO_USER_FOUND, String.valueOf(HttpStatus.NOT_FOUND)));
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return ApiResponseBuilder containing the user response if found
     */
    public ApiResponseBuilder<UserResponse> getUserByEmail(final String email) {
       return userRepository.findUsersByEmail(email)
               .map(userMapper::mapFromEntityToResponse)
               .map(response -> ApiResponse.success(response, LogMessages.SUCCESS_RESPONSE))
               .orElseGet(() -> ApiResponse.failure(LogMessages.NO_USER_FOUND, String.valueOf(HttpStatus.NOT_FOUND)));
    }

    /**
     * Retrieves a user by their full name.
     *
     * @param name the full name of the user
     * @return ApiResponseBuilder containing the user response if found
     */
    public ApiResponseBuilder<UserResponse> getUserByName(final String name) {
       return userRepository.findUserByFullName(name)
               .map(userMapper::mapFromEntityToResponse)
               .map(response -> ApiResponse.success(response, LogMessages.SUCCESS_RESPONSE))
               .orElseGet(() -> ApiResponse.failure(LogMessages.NO_USER_FOUND, String.valueOf(HttpStatus.NOT_FOUND)));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return ApiResponseBuilder indicating the result of the deletion operation
     */
    @Transactional
    public ApiResponseBuilder<String> deleteUserById(final Integer id) {
        var user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty()) {
            return ApiResponse.failure(LogMessages.NO_USER_FOUND, String.valueOf(HttpStatus.NOT_FOUND));
        }
        userRepository.deleteById(Long.valueOf(id));
        return ApiResponse.success(LogMessages.DELETED);
    }
}