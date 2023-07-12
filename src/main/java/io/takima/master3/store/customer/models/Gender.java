package io.takima.master3.store.customer.models;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum Gender {

    MALE('m'),
    FEMALE('f');

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public static Gender fromCode(char code) {
        return switch (code) {
            case 'm' -> MALE;
            case 'f' -> FEMALE;
            default -> throw new UnsupportedOperationException(
                    "The code " + code + " is not supported!");
        };
    }

    public char getCode() {
        return code;
    }

    @Converter
    public static class GenderConverter
            implements AttributeConverter<Gender, Character> {

        public Character convertToDatabaseColumn(Gender value) {
            if (value == null) {
                return null;
            }

            return value.getCode();
        }

        public Gender convertToEntityAttribute(Character value) {
            if (value == null) {
                return null;
            }

            return Gender.fromCode(value);
        }
    }
}


