package am.registration.system.demo.configuration.db;

import am.registration.system.demo.exception.PermissionNotFoundException;
import am.registration.system.demo.model.entity.Permission;
import am.registration.system.demo.model.entity.Role;
import am.registration.system.demo.model.enums.Permissions;
import am.registration.system.demo.model.enums.Roles;
import am.registration.system.demo.model.repository.PermissionRepository;
import am.registration.system.demo.model.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static am.registration.system.demo.model.enums.Permissions.*;
import static am.registration.system.demo.model.enums.Roles.*;

/**
 * Component responsible for initializing roles and permissions in the system.
 * <p>
 * The DataInitializer class ensures that all necessary roles and permissions are
 * present in the database upon application startup. It runs automatically after the
 * bean construction, leveraging the {@code @PostConstruct} annotation.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 14.02.25
 */
@Component
@Transactional
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    /**
     * Initializes the roles and permissions in the database.
     * <p>
     * This method is automatically invoked after bean construction to ensure that
     * essential roles and permissions are available in the system.
     * </p>
     */
    @PostConstruct
    public void initialize() {
        initializePermissions();
        initializeRoles();
    }

    /**
     * Initializes the roles and their associated permissions.
     * <p>
     * This method defines a map of roles and their corresponding permissions.
     * It retrieves existing roles from the database or creates new ones if they
     * do not exist. It then assigns the relevant permissions to each role.
     * </p>
     */
    private void initializeRoles() {
        Map<Roles, Set<Permissions>> rolePermissions = Map.of(
                GUEST, Set.of(VIEW_PUBLIC_CONTENT),
                USER, Set.of(UPDATE_PROFILE, CREATE_CONTENT, VIEW_OWN_CONTENT),
                MANAGER, Set.of(APPROVE_CONTENT, REJECT_CONTENT, MANAGE_ORDERS, VIEW_REPORTS, MODERATE_USERS, ASSIGN_TASKS),
                ADMIN, Set.of(MANAGE_USERS, ASSIGN_ROLES, DELETE_CONTENT, CONFIGURE_SYSTEM, ACCESS_LOGS, MANAGE_SECURITY)
        );

        rolePermissions.forEach((roleEnum, permissionEnums) -> {
            Role role = roleRepository.findByRoles(roleEnum)
                    .orElseGet(() -> new Role(roleEnum, new HashSet<>()));

            Set<Permission> managedPermissions = permissionEnums.stream()
                    .map(permissionEnum -> permissionRepository.findByPermissions(permissionEnum)
                            .orElseThrow(() -> new PermissionNotFoundException("Permission not found: " + permissionEnum)))
                    .collect(Collectors.toSet());

            role.setPermissions(managedPermissions);
            roleRepository.save(role);
        });
    }

    /**
     * Initializes the permissions required by the application.
     * <p>
     * This method checks whether each permission already exists in the database.
     * If a permission is not found, it is created and saved.
     * </p>
     */
    private void initializePermissions() {
        Set<Permissions> permissions = Set.of(
                VIEW_PUBLIC_CONTENT, UPDATE_PROFILE, CREATE_CONTENT, VIEW_OWN_CONTENT,
                APPROVE_CONTENT, REJECT_CONTENT, MANAGE_ORDERS, VIEW_REPORTS, MODERATE_USERS, ASSIGN_TASKS,
                MANAGE_USERS, ASSIGN_ROLES, DELETE_CONTENT, CONFIGURE_SYSTEM, ACCESS_LOGS, MANAGE_SECURITY
        );

        for (var permissionEnum : permissions) {
            Optional<Permission> existingPermission = permissionRepository.findByPermissions(permissionEnum);
            if (existingPermission.isPresent()) {
                permissionRepository.save(existingPermission.get());
            } else {
                permissionRepository.save(new Permission(permissionEnum));
            }
        }
    }
}