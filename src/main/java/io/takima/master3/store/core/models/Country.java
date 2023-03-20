package io.takima.master3.store.core.models;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public enum Country {
    AUSTRALIA("AU", Currency.DOLLAR),
    BELGIUM("BE", Currency.EURO),
    BRAZIL("BR", Currency.DOLLAR),
    CANADA("CA", Currency.DOLLAR),
    CZECH_REPUBLIC("CZ", Currency.EURO),
    CHINA("CN", Currency.DOLLAR),
    FRANCE("FR", Currency.EURO),
    GERMANY("DE", Currency.EURO),
    GREAT_BRITAIN("GB", Currency.POUND),
    INDIA("IND", Currency.DOLLAR),
    INDONESIA("ID", Currency.DOLLAR),
    ITALY("IT", Currency.EURO),
    JAPAN("JPN", Currency.JPY),
    KOREA("KO", Currency.JPY),
    MEXICO("MX", Currency.DOLLAR),
    NEPAL("NEP", Currency.DOLLAR),
    POLAND("PL", Currency.EURO),
    RUSSIA("RU", Currency.ROUBLE),
    SPAIN("ES", Currency.EURO),
    SWITZERLAND("CH", Currency.DOLLAR),
    TURKY("TR", Currency.DOLLAR),
    UNITED_KINGDOM("UK", Currency.POUND),
    USA("US", Currency.DOLLAR),
    VIETNAM("VI", Currency.DOLLAR);


    public static Map<String, Country> lut = new HashMap<>();
    public final String code;
    public final Currency currency;

    Country(String code, Currency currency) {
        this.code = code;
        this.currency = currency;
    }

    public static Country fromCode(String code) {
        return lut.computeIfAbsent(code, (s) -> Arrays.stream(values())
                        .filter(c -> c.code.equals(code))
                        .findAny()
                        .orElse(Country.USA) //UGLY, juste pour pas avoir des erreurs a chaque pays que j'ai eu la fleme de rajouter
//                .orElseThrow(() -> new IllegalArgumentException("no currency found for code " + code))

        );
    }

    public Currency getCurrency() {
        return currency;
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
