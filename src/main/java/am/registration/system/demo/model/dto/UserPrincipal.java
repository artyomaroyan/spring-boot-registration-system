package am.registration.system.demo.model.dto;

import am.registration.system.demo.model.entity.Role;
import am.registration.system.demo.model.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The UserPrincipal class implements the {@link UserDetails} interface, representing the authenticated
 * user in the Spring Security context. It provides necessary user information and authorities (roles)
 * required for authentication and authorization.
 * *
 * * Implements:
 * - UserDetails: Enables integration with Spring Security.
 * - Serializable: Allows the object to be serialized, essential for distributed systems.
 * *
 * * Annotations:
 * - @Getter: Generates getter methods for all fields using Lombok.
 * - @NoArgsConstructor: Generates a no-arguments constructor using Lombok.
 * - @AllArgsConstructor: Generates a constructor with all fields using Lombok.
 * *
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String password;
    private String email;
    private transient Set<Role> authorities;
    private UserState userState;

    /**
     * Retrieves the role names as a set of strings from the granted authorities.
     *
     * @return A set of role names assigned to the user.
     */
    public Set<String> getRoles() {
        return this.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return convertRolesToAuthorities(authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Converts the set of {@link Role} objects to a collection of {@link GrantedAuthority}.
     * Prepends the "ROLE_" prefix to the role name as required by Spring Security.
     *
     * @param roles The set of roles to convert.
     * @return A collection of {@link GrantedAuthority} objects.
     */
    private Collection<? extends GrantedAuthority> convertRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        "ROLE_" + role.getRoles().name().toUpperCase()))
                .collect(Collectors.toSet());
    }
}