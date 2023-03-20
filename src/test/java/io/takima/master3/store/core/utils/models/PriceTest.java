package io.takima.master3.store.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


class PriceTest {
    @Test
    void convertDollarToEuroShouldBeOk() {
        assertEquals(
                new Price(.88, Currency.EURO),
                new Price(1, Currency.DOLLAR).convertTo(Currency.EURO)
        );
    }
    @Test

    void convertEuroDollarShouldBeOk() {
        assertEquals(
                new Price(1.14, Currency.DOLLAR),
                new Price(1, Currency.EURO).convertTo(Currency.DOLLAR)
        );
    }
    @Test
    @DisplayName("should add the amount")
    void plusShouldBeOk() {
        assertEquals(
                new Price(1, Currency.DOLLAR).plus(new Price(2, Currency.DOLLAR)),
                new Price(3, Currency.DOLLAR)
        );
    }
    @Test
    void minusShouldBeOk() {
        assertEquals(
                new Price(5, Currency.DOLLAR).minus(new Price(2, Currency.DOLLAR)),
                new Price(3, Currency.DOLLAR)
        );
    }
    @Test
    void multiplyShouldBeOk() {
        assertEquals(
                new Price(5, Currency.DOLLAR).multiply(2),
                new Price(10, Currency.DOLLAR)
        );
    }
}
