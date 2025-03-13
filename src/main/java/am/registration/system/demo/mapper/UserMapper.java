package am.registration.system.demo.mapper;

import am.registration.system.demo.model.dto.UserPrincipal;
import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.dto.UserResponse;
import am.registration.system.demo.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper class for transforming User-related objects.
 * <p>
 * The UserMapper component handles mapping between entity objects (User),
 * DTOs (UserRequest and UserResponse), and security objects (UserPrincipal).
 * It leverages ModelMapper for object conversion and UserFactory for user creation.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 15.02.25
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final UserFactory userFactory;

    /**
     * Maps a User entity to a UserResponse DTO.
     *
     * @param user the User entity to be mapped
     * @return the mapped UserResponse DTO
     */
    public UserResponse mapFromEntityToResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    /**
     * Maps a UserResponse DTO to a User entity.
     *
     * @param response the UserResponse DTO to be mapped
     * @return the mapped User entity
     */
    public User mapFromResponseToEntity(UserResponse response) {
        return modelMapper.map(response, User.class);
    }

    /**
     * Maps a list of User entities to a list of UserResponse DTOs.
     * Uses stream API for efficient mapping.
     *
     * @param users the list of User entities to be mapped
     * @return the list of mapped UserResponse DTOs
     */
    public List<UserResponse> mapFromEntityListToResponseList(List<User> users) {
        return users.stream()
                .map(this::mapFromEntityToResponse)
                .toList();
    }

    /**
     * Maps a UserRequest DTO to a User entity.
     * Uses the UserFactory to create a new User object.
     *
     * @param request the UserRequest DTO containing user input data
     * @return the created User entity
     */
    public User mapFromRequestToEntity(UserRequest request) {
        return userFactory.createUser(request);
    }

    /**
     * Maps a User entity to a UserPrincipal for authentication purposes.
     *
     * @param user the User entity to be mapped
     * @return the mapped UserPrincipal object
     */
    public UserPrincipal mapFromEntityToPrincipal(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRoles(),
                user.getUserState()
        );
    }
}