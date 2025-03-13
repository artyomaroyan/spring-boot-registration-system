package am.registration.system.demo.mapper;

import am.registration.system.demo.model.dto.UserRequest;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.enums.UserState;
import am.registration.system.demo.security.password.Argon2Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Factory class for creating User entities.
 * <p>
 * The UserFactory component is responsible for building {@link User} objects
 * with default roles and securely hashed passwords. It uses the
 * {@link RoleMapper} to assign default roles and {@link Argon2Hashing} to
 * securely hash user passwords.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 15.02.25
 */
@Component
@RequiredArgsConstructor
public class UserFactory {

    private final RoleMapper roleMapper;
    private final Argon2Hashing argon2Hashing;

    /**
     * Creates a new User entity based on the provided user request.
     * <p>
     * The password is securely hashed using the Argon2 algorithm, and the
     * default roles are retrieved through the RoleMapper. The created user
     * is initialized with a pending state.
     * </p>
     *
     * @param request the user request containing user details
     * @return the newly created User entity
     */
    protected User createUser(UserRequest request) {
        return new User(
                request.getUsername(),
                request.getFullName(),
                argon2Hashing.encode(request.getPassword()),
                request.getEmail(),
                request.getPhone(),
                request.getAge(),
                UserState.PENDING,
                roleMapper.getDefaultRoles(),
                new HashSet<>()
        );
    }
}