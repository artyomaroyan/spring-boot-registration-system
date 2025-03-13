package am.registration.system.demo.mapper;

import am.registration.system.demo.model.entity.Role;
import am.registration.system.demo.model.enums.Roles;
import am.registration.system.demo.model.repository.RoleRepository;
import am.registration.system.demo.service.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Maps and provides role-related operations.
 * <p>
 * The RoleMapper component is responsible for retrieving and creating default roles.
 * It interacts with the {@link RoleRepository} to fetch roles from the database and
 * with the {@link PermissionService} to get permissions associated with a specific role.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    /**
     * Retrieves the default user roles from the database.
     * <p>
     * If the default role does not exist, it will be created with the necessary permissions
     * and saved to the database.
     * </p>
     *
     * @return a set containing the default role
     */
    public Set<Role> getDefaultRoles() {
        var role = Roles.USER;
        return roleRepository.findByRoles(Roles.valueOf(role.name()))
                .map(Set::of)
                .orElseGet(() -> {
                    var permission = permissionService.getPermissionsByRole(role);
                    var newRole = new Role(role, permission);
                    return Set.of(roleRepository.save(newRole));
                });
    }
}