package io.takima.master3.store.core.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class PriceTest {
    @Test
    void convertDollarToEuroShouldBeOk() {
        assertEquals(
                new Price(.88, Currency.EURO),
                new Price(1, Currency.DOLLAR).convertTo(Currency.EURO)
        );
    }
}
