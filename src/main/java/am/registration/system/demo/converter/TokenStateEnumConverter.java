package am.registration.system.demo.converter;

import am.registration.system.demo.security.token.enums.TokenState;
import jakarta.persistence.Converter;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Converter(autoApply = true)
public class TokenStateEnumConverter extends EnumConverter<TokenState> {
    public TokenStateEnumConverter() {
        super(TokenState.class);
    }
}