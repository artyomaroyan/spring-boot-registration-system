package am.registration.system.demo.model.entity;

import am.registration.system.demo.converter.UserStateEnumConverter;
import am.registration.system.demo.model.enums.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

/**
 * The User entity represents a system user with personal details, authentication information,
 * and associated roles and tokens. This class extends BaseEntity to inherit common properties
 * such as ID, created date, and updated date.
 * *
 * * Relationships:
 * - Roles: A user can have multiple roles, mapped via a many-to-many relationship.
 * - UserTokens: A user can have multiple authentication tokens, mapped via a one-to-many relationship.
 *  *
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
@Table(name = "usr", schema = "registration_db")
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private int age;

    @Convert(converter = UserStateEnumConverter.class)
    private UserState userState;

    /**
     * A set of roles assigned to the user, allowing for flexible role-based access control.
     * *
     * * Annotations:
     * - @ManyToMany: Represents a many-to-many relationship between users and roles.
     * - fetch = LAZY: Loads roles only when accessed, saving memory and processing.
     * - cascade = {MERGE, PERSIST}: Allows automatic merging and persisting of related roles.
     * - @JoinTable: Defines a join table with foreign keys for users and roles.
     */
    @ManyToMany(fetch = LAZY, cascade = {MERGE, PERSIST})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    /**
     * A set of user tokens associated with this user, used for authentication and session management.
     * *
     * * Annotations:
     * - @OneToMany: Represents a one-to-many relationship between users and tokens.
     * - mappedBy = "user": Specifies that the relationship is managed by the "user" field in UserToken.
     * - fetch = LAZY: Loads tokens only when accessed.
     * - cascade = {ALL}: Allows all operations (persist, merge, remove) to be cascaded to related tokens.
     * - orphanRemoval = true: Ensures that tokens are deleted when they are no longer associated with the user.
     */
    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = {ALL}, orphanRemoval = true)
    private Set<UserToken> userToken;
}