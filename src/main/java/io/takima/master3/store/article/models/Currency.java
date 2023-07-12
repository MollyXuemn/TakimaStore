package io.takima.master3.store.article.models;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;

public enum Currency {
    DOLLAR("USD", 1.0),
    EURO("EUR", 0.88),
    JPY("JPY", 113.22),
    ROUBLE("RUB", 0.015),
    POUND("GBP", 0.79);

    public final String symbol;
    public final double rate;

    public static Map<String, Currency> lut = new HashMap<>();

    Currency(String symbol, double rate) {
        this.symbol = symbol;
        this.rate = rate;
    }

    public static Currency fromSymbol(String symbol) {
        return lut.computeIfAbsent(symbol, (s) -> Arrays.stream(values())
                .filter(c -> c.symbol.equals(symbol))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("no currency found for symbol " + symbol))
        );
    }

    @Converter(autoApply = true)
    public static class CurrencyConverter
            implements AttributeConverter<Currency, String> {
        public String convertToDatabaseColumn(Currency currency) {
            if (currency == null) {
                return null;
            }

            return currency.symbol;
        }

        public Currency convertToEntityAttribute(String dbColumn) {
            if (dbColumn == null) {
                return null;
            }

            return Currency.fromSymbol("" + dbColumn);
        }
    }
}

