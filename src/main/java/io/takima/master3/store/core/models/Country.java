package io.takima.master3.store.core.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Country {
    SPAIN("ES", Currency.EUR),
    JAPAN("JPN", Currency.JPY),
    GREAT_BRITAIN("GB", Currency.GBP),
    INDONESIA("ID", Currency.RUB),
    VIETNAM("VI", Currency.USD),
    CHINA("CN", Currency.USD),
    MEXICO("MX", Currency.USD),
    UNITED_STATES("US", Currency.USD),
    CANADA("CA", Currency.USD),
    DENMARK("DE", Currency.EUR),
    ITALY("IT", Currency.EUR),
    UNITED_KINGDOM("UK", Currency.GBP),
    BRAZIL("BR", Currency.USD),
    INDIA("IND", Currency.RUB);


    public final String code;

    public final Currency currency;

    public static Map<String, Country> lut = new HashMap<>();

    Country(String code, Currency currency) {
        this.code = code;
        this.currency = currency;
    }

    public static Country fromCode(String code) {
        return lut.computeIfAbsent(code, (s) -> Arrays.stream(values())
                .filter(c -> c.code.equals(code))
                .findAny()
                .orElse(Country.BRAZIL) //.orElseThrow(NoCountryException)
        );
    }

    @Converter(autoApply = true)
    public static class CountryConverter
            implements AttributeConverter<Country, String> {

        public String convertToDatabaseColumn(Country country) {
            if (country == null) {
                return null;
            }

            return country.code;
        }

        public Country convertToEntityAttribute(String dbColumn) {
            if (dbColumn == null) {
                return null;
            }

            return Country.fromCode(dbColumn);
        }
    }
}
