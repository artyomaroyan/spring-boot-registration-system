package am.registration.system.demo.util;

import am.registration.system.demo.service.user.management.UserManagementService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * A custom implementation of the {@link PermissionEvaluator} interface that determines whether
 * a user has a specific permission for a given domain object. This class integrates with Spring Security
 * to perform fine-grained access control.
 * *
 * * Annotations:
 * - @Component: Marks this class as a Spring bean, allowing it to be injected and managed by the Spring context.
 * - @NoArgsConstructor: Generates a no-arguments constructor using Lombok.
 * - @AllArgsConstructor: Generates an all-arguments constructor using Lombok.
 * *
 * * Dependencies:
 * - UserManagementService: Used to verify whether the current authenticated user has access to the target domain object.
 * *
 * Author: Artyom Aroyan
 * Date: 25.02.25
 * Time: 01:08:01
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private UserManagementService userManagementService;

    /**
     * Evaluates whether the given authentication has permission to access the specified domain object.
     * This method is primarily used for fine-grained authorization.
     *
     * @param authentication The authentication object representing the current user.
     * @param targetDomainObject The domain object ID (should be an integer representing the user ID).
     * @param permission The required permission (not used in this implementation).
     * @return {@code true} if the authenticated user has permission to access the object, {@code false} otherwise.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !(targetDomainObject instanceof Integer userId)) {
            return false;
        }
        final String username = authentication.getName();
        return userManagementService.isCurrentUser(userId, username);
    }

    /**
     * This method is not implemented and always returns {@code false}.
     * Typically used for evaluating permissions when the target domain object is represented by an ID and type.
     *
     * @param authentication The authentication object representing the current user.
     * @param targetId The ID of the target domain object.
     * @param targetType The type of the target domain object (not used in this implementation).
     * @param permission The required permission (not used in this implementation).
     * @return {@code false}, as this method is not implemented.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}