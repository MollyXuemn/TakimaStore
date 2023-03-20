package io.takima.master3.store.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class PriceTest {
    @Nested
    @DisplayName("should convert currency")
    class ConvertTest {
        @Test
        @DisplayName("should convert the currency Dollar To Euro ")
        void convertDollarToEuroShouldBeOk() {
            assertEquals(
                    new Price(.88, Currency.EURO),
                    new Price(1, Currency.DOLLAR).convertTo(Currency.EURO)
            );
        }
        @Test
        @DisplayName("should convert the currency Euro To Dollar")
        void convertEuroDollarShouldBeOk() {
            assertEquals(
                    new Price(1.14, Currency.DOLLAR),
                    new Price(1, Currency.EURO).convertTo(Currency.DOLLAR)
            );
        }
    }
    @Nested
    @DisplayName("should add currency")
    class AddTest{
        @Test
        @DisplayName("should add the amount")
        void plusShouldBeOk() {
            assertEquals(
                    new Price(1, Currency.DOLLAR).plus(new Price(2, Currency.DOLLAR)),
                    new Price(3, Currency.DOLLAR)
            );

            assertEquals(
                    new Price(2, Currency.EURO).plus(new Price(3, Currency.EURO)),
                    new Price(5, Currency.EURO)
            );
        }
    }
    @Nested
    @DisplayName("Test subtract method")
    class SubtractTest{
        @Test
        @DisplayName("should minus the amount")
        void minusShouldBeOk() {
            assertEquals(
                    new Price(5, Currency.DOLLAR).minus(new Price(2, Currency.DOLLAR)),
                    new Price(3, Currency.DOLLAR)
            );
        }
    }
    @Nested
    @DisplayName("Test multiply method")
    class MultiplyTest {
        @Test
        @DisplayName("should multiply the amount")
        void multiplyShouldBeOk() {
            assertEquals(
                    new Price(5, Currency.DOLLAR).multiply(2),
                    new Price(10, Currency.DOLLAR)
            );
        }
    }


    @Nested
    @DisplayName("method 'compareTo(Price)'")
    class CompareToMethod {
        @Test
        @DisplayName("should return anything < 0 if given price is higher")
        void shouldCompareWithSuperior() {
            assertThat(new Price(1.13, Currency.DOLLAR)
                    .compareTo(new Price(1, Currency.EURO))).isLessThan(0);
        }

        @Test
        @DisplayName("should return anything > 0 if given price is lower")
        void shouldCompareWithInferior() {
            assertThat(new Price(1.15, Currency.DOLLAR)
                    .compareTo(new Price(1, Currency.EURO))).isGreaterThan(0);
        }

        @Test
        @DisplayName("should return 0 if given price is of same value")
        void shouldCompareWithEqual() {
            assertThat(new Price(1.14, Currency.DOLLAR)
                    .compareTo(new Price(1, Currency.EURO))
            ).isEqualTo(0);
        }

    }

}
