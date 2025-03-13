package am.registration.system.demo.service.user;

import am.registration.system.demo.model.dto.UserPrincipal;
import am.registration.system.demo.model.entity.User;
import am.registration.system.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the UserDetailsService interface used for loading user-specific data.
 * This service is used during authentication to load user details from the database by username.
 * *
 * Author: Artyom Aroyan
 * Date: 17.02.25
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads a user by their username from the database.
     * This method is called during authentication to retrieve user details.
     *
     * @param username the username identifying the user whose data is required
     * @return UserDetails containing user information such as username, password, roles, and state
     * @throws UsernameNotFoundException if no user with the given username is found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found:"));

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
