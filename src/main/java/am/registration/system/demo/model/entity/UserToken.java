package am.registration.system.demo.model.entity;

import am.registration.system.demo.converter.TokenPurpoesEnumConverter;
import am.registration.system.demo.converter.TokenStateEnumConverter;
import am.registration.system.demo.security.token.enums.TokenPurpose;
import am.registration.system.demo.security.token.enums.TokenState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.FetchType.LAZY;

/**
 * The UserToken entity represents an authentication or verification token associated with a user.
 * These tokens are used for various purposes such as authentication, password reset, or account verification.
 * *
 * * Relationships:
 * - User: Each token is associated with a single user through a many-to-one relationship.
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
@Table(name = "user_token", schema = "registration_db")
public class UserToken extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private Date expireDate;

    @Convert(converter = TokenPurpoesEnumConverter.class)
    private TokenPurpose tokenPurpose;
    @Convert(converter = TokenStateEnumConverter.class)
    private TokenState tokenState;

    /**
     * The user associated with this token.
     * *
     * * Annotations:
     * - @ManyToOne: Represents a many-to-one relationship between tokens and users.
     * - fetch = LAZY: Loads the associated user only when explicitly accessed, improving performance.
     * - @JoinColumn: Specifies the foreign key column name and references the "id" field in the User entity.
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}