package am.registration.system.demo.converter;

import am.registration.system.demo.exception.UnknownEmailValueException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

/**
 * A generic JPA attribute converter for enumerations, allowing automatic conversion between
 * enum constants and their string representation in the database.
 * *
 * This converter can be used for any enum type by specifying the enum class as a generic parameter.
 * *
 * * Annotations:
 * - @Converter(autoApply = true): Marks this as a JPA converter that automatically applies to all enum fields.
 * *
 * * Usage:
 * - This converter maps an enum constant to its name (String) in the database and vice versa.
 * *
 * @param <T> the type of the enum being converted
 * *
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Converter(autoApply = true)
class EnumConverter<T extends Enum<T>> implements AttributeConverter<T, String> {

    private final Class<T> enumType;

    protected EnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    /**
     * Converts the given enum constant to a string representation suitable for database storage.
     *
     * @param attribute the enum constant to be converted
     * @return the string representation of the enum constant, or null if the attribute is null
     */
    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.name() : null;
    }

    /**
     * Converts the given database value to an enum constant.
     *
     * @param dbData the string value from the database
     * @return the corresponding enum constant, or null if the database value is null
     * @throws UnknownEmailValueException if the value does not match any enum constant
     */
    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(enumType.getEnumConstants())
                .filter(e -> e.name().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new UnknownEmailValueException("Unknown enum value: " + dbData + " for enum: " + enumType.getSimpleName()));
    }
}