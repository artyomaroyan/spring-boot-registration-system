package am.registration.system.demo.service.permission;

import am.registration.system.demo.model.entity.Permission;
import am.registration.system.demo.model.enums.Roles;
import am.registration.system.demo.model.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service class responsible for managing permissions related to user roles.
 * Provides methods to retrieve permissions associated with specific roles.
 * *
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    /**
     * Retrieves the set of permissions associated with a given role.
     *
     * @param roleName the name of the role whose permissions are being retrieved
     * @return a set of permissions associated with the specified role
     */
    public Set<Permission> getPermissionsByRole(Roles roleName) {
        return permissionRepository.findPermissionByRoles(roleName);
    }
}