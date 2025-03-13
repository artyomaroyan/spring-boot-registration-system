package am.registration.system.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * BaseEntity is an abstract class that serves as the foundation for all entity classes
 * within the application. It provides common fields such as id, createdDate, and updatedDate,
 * and handles automatic timestamp updates during persistence and updates.
 * *
 * The class is annotated with @MappedSuperclass to indicate that it is not an entity itself
 * but will provide mapping information to subclasses.
 * *
 * * Annotations:
 * - @Getter: Generates getter methods for all fields using Lombok.
 * - @Setter: Generates a setter method for the updatedDate field.
 * - @MappedSuperclass: Indicates that this class provides persistent fields to subclasses.
 * - @CreationTimestamp and @UpdateTimestamp: Automatically manage timestamps for creation and update events.
 * *
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @CreationTimestamp
    private Date createdDate;
    @Setter
    @UpdateTimestamp
    private Date updatedDate;
}