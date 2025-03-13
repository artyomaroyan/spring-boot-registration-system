package am.registration.system.demo.model.entity;

import am.registration.system.demo.converter.PermissionEnumConverter;
import am.registration.system.demo.model.enums.Permissions;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * The Permission entity represents a specific permission that can be assigned to roles,
 * enabling fine-grained access control within the application.
 * *
 * * Relationships:
 * - Roles: A permission can be associated with multiple roles through a many-to-many relationship.
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
@Table(name = "permission", schema = "registration_db")
public class Permission extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Convert(converter = PermissionEnumConverter.class)
    private Permissions permissions;

    /**
     * A set of roles associated with this permission, forming a bidirectional many-to-many relationship.
     * Mapped by the "permissions" field in the Role entity.
     * *
     * * Annotations:
     * - @ManyToMany: Represents a many-to-many relationship between permissions and roles.
     * - mappedBy = "permissions": Indicates that the relationship is owned by the "permissions" field in Role.
     */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    public Permission(Permissions permissions) {
        this.permissions = permissions;
    }
}