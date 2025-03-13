package am.registration.system.demo.model.repository;

import am.registration.system.demo.model.entity.Role;
import am.registration.system.demo.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Artyom Aroyan
 * Date: 14.02.25
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoles(Roles roles);
}