package am.registration.system.demo.model.entity;

import am.registration.system.demo.converter.RoleEnumConverter;
import am.registration.system.demo.model.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

/**
 * The Role entity represents a specific role within the application, defining a set of permissions
 * that can be granted to users. It supports flexible role-based access control through associations
 * with permissions and users.
 * *
 * * Relationships:
 * - Permissions: A role can have multiple permissions, mapped via a many-to-many relationship.
 * - Users: A role can be associated with multiple users through the user-role relationship.
 * *
 * * Annotations:
 * - @Entity: Marks the class as a JPA entity.
 * - @Table: Specifies the table name and schema in the database.
 * - @Getter, @Setter: Generate getter and setter methods using Lombok.
 * - @NoArgsConstructor, @AllArgsConstructor: Generate constructors using Lombok.
 * *
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role", schema = "registration_db")
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Convert(converter = RoleEnumConverter.class)
    private Roles roles;

    /**
     * A set of permissions associated with this role, allowing for fine-grained access control.
     * *
     * * Annotations:
     * - @ManyToMany: Represents a many-to-many relationship between roles and permissions.
     * - fetch = LAZY: Loads permissions only when accessed, optimizing performance.
     * - cascade = {MERGE, PERSIST, REFRESH}: Propagates changes, creation, and refresh operations to related permissions.
     * - @JoinTable: Specify the join table to link roles and permissions.
     */
    @ManyToMany(fetch = LAZY, cascade = {MERGE, PERSIST, REFRESH})
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;
}