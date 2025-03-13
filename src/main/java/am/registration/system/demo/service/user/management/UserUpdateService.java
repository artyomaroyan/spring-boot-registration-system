package am.registration.system.demo.service.user.management;

import am.registration.system.demo.api.response.ApiResponse;
import am.registration.system.demo.api.response.ApiResponseBuilder;
import am.registration.system.demo.exception.UserNotFoundException;
import am.registration.system.demo.mapper.UserMapper;
import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.dto.UserResponse;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.repository.UserRepository;
import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Service for updating user information.
 * Provides functionality to update user details while preserving existing data.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 13:50:03
 */
@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserManagementService userManagementService;

    /**
     * Updates user details based on the provided request.
     *
     * @param id      the ID of the user to update
     * @param request the user request containing updated information
     * @return ApiResponseBuilder containing the updated user response
     */
    @Transactional
    public ApiResponseBuilder<UserResponse> update(final Integer id, final UserRequest request) {
        var user = getUserForUpdate(id);
        var update = updateUser(user, request);
        return buildSuccessResponse(update);
    }

    /**
     * Fetches the user by ID.
     *
     * @param id the ID of the user
     * @return the fetched user
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    private User getUserForUpdate(final Integer id) {
        return userMapper.mapFromResponseToEntity(userManagementService.getUserById(id).data());
    }

    /**
     * Updates the user details with the data from the request.
     *
     * @param user    the existing user entity
     * @param request the user request containing updated information
     * @return the updated user entity
     */
    private User updateUser(final User user, final UserRequest request) {
        user.setUpdatedDate(new Date());
        Optional.ofNullable(request.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(request.getFullName()).ifPresent(user::setFullName);
        Optional.ofNullable(request.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(request.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(user::setPhone);
        Optional.of(request.getAge()).ifPresent(age -> user.setAge(age != 18 ? age : user.getAge()));
        return userRepository.saveAndFlush(user);
    }

    /**
     * Builds a success response for the updated user.
     *
     * @param user the updated user entity
     * @return ApiResponseBuilder containing the updated user response
     */
    private ApiResponseBuilder<UserResponse> buildSuccessResponse(final User user) {
        var result = userMapper.mapFromEntityToResponse(user);
        return ApiResponse.success(result, LogMessages.USER_SUCCESSFULLY_UPDATED);
    }
}