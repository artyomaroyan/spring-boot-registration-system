package am.registration.system.demo.converter;

import am.registration.system.demo.model.enums.UserState;
import jakarta.persistence.Converter;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Converter(autoApply = true)
public class UserStateEnumConverter extends EnumConverter<UserState> {
    public UserStateEnumConverter() {
        super(UserState.class);
    }
}