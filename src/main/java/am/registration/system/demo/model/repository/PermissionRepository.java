package am.registration.system.demo.model.repository;

import am.registration.system.demo.model.entity.Permission;
import am.registration.system.demo.model.enums.Permissions;
import am.registration.system.demo.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissions(Permissions permissionEnum);

    @Query("select p from Permission p join p.roles r where r.roles = :roleName")
    Set<Permission> findPermissionByRoles(@Param("roleName") Roles roleName);
}