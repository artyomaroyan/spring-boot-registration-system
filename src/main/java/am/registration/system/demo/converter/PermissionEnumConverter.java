package am.registration.system.demo.converter;

import am.registration.system.demo.model.enums.Permissions;
import jakarta.persistence.Converter;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Converter(autoApply = true)
public class PermissionEnumConverter extends EnumConverter<Permissions> {
    public PermissionEnumConverter() {
        super(Permissions.class);
    }
}