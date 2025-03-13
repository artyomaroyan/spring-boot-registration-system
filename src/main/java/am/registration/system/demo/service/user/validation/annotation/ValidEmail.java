package am.registration.system.demo.service.user.validation.annotation;

import am.registration.system.demo.service.user.validation.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for validating email addresses.
 * Uses the {@link EmailValidator} class to perform validation.
 * It Can be applied to fields and method parameters.
 * *
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 00:27:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {

    /**
     * Returns the default error message when validation fails.
     *
     * @return the error message
     */
    String message() default "Invalid email";

    /**
     * Used to specify validation groups.
     *
     * @return the array of groups
     */
    Class<?>[] groups() default {};

    /**
     * Used to specify additional payload data.
     *
     * @return the array of payload classes
     */
    Class<? extends Payload>[] payload() default {};
}