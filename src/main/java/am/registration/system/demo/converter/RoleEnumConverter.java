package am.registration.system.demo.converter;

import am.registration.system.demo.model.enums.Roles;
import jakarta.persistence.Converter;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Converter(autoApply = true)
public class RoleEnumConverter extends EnumConverter<Roles> {
    public RoleEnumConverter() {
        super(Roles.class);
    }
}