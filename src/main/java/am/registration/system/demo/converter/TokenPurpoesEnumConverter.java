package am.registration.system.demo.converter;

import am.registration.system.demo.security.token.enums.TokenPurpose;
import jakarta.persistence.Converter;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Converter(autoApply = true)
public class TokenPurpoesEnumConverter extends EnumConverter<TokenPurpose> {
    public TokenPurpoesEnumConverter() {
        super(TokenPurpose.class);
    }
}