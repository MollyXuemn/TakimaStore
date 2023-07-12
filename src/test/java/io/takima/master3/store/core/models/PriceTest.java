package io.takima.master3.store.core.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("class Price")
class PriceTest {

    @Nested
    @DisplayName("method 'convertTo(Currency)'")
    class ConvertTo {
        @Nested
        @DisplayName("from Currency = DOLLAR")
        class GivenDollar {

            @Test
            @DisplayName("should be able to convert to euros")
            void convertDollarToEuroShouldBeOk() {
                assertEquals(
                        new Price(.88, Currency.EUR),
                        new Price(1, Currency.USD).convertTo(Currency.EUR)
                );
            }
        }

        @Nested
        @DisplayName("from Currency = EURO")
        class GivenEuro {

            @Test
            @DisplayName("should be able to convert to dollars")
            void convertEuroDollarShouldBeOk() {
                assertEquals(
                        new Price(1.14, Currency.USD),
                        new Price(1, Currency.EUR).convertTo(Currency.USD)
                );
            }
        }

        @Nested
        @DisplayName("from Currency = POUNDS")
        class GivenPounds {


            @Test
            @DisplayName("should be able to convert to JYP")
            void convertGBPJPYShouldBeOk() {
                assertEquals(
                        new Price(143.32, Currency.JPY),
                        new Price(1, Currency.GBP).convertTo(Currency.JPY)
                );
            }
        }
    }

    @Nested
    @DisplayName("method 'plus(Price)'")
    class PlusMethod {

        @Nested
        @DisplayName("given a price of the same currency")
        class WithSameCurrency {

            @Test
            @DisplayName("should add the given amount")
            void shouldAddAmount() {
                assertEquals(
                        new Price(1, Currency.USD).plus(new Price(2, Currency.USD)),
                        new Price(3, Currency.USD)
                );
            }
        }

        @Nested
        @DisplayName("given a price of different currency")
        class WithDifferentCurrency {

            @Test
            @DisplayName("should convert the price into the proper currency")
            void shouldConvertCurrency() {
                assertEquals(Currency.USD,
                        new Price(1, Currency.USD).plus(new Price(2, Currency.EUR)).currency
                );
            }

            @Test
            @DisplayName("should add the given amount")
            void shouldAddAmount() {
                assertEquals(
                        new Price(1, Currency.USD).plus(new Price(2, Currency.EUR)),
                        new Price(3.27, Currency.USD)
                );
            }
        }
    }


    @Nested
    @DisplayName("method 'minus(Price)'")
    class MinusMethod {

        @Nested
        @DisplayName("given a price of the same currency")
        class WithSameCurrency {

            @Test
            @DisplayName("should subtract the given amount")
            void shouldAddAmount() {
                assertEquals(new Price(3, Currency.USD),
                        new Price(10, Currency.USD).minus(new Price(7, Currency.USD))
                );
            }
        }

        @Nested
        @DisplayName("given a price of different currency")
        class WithDifferentCurrency {

            @Test
            @DisplayName("should convert the price into the proper currency")
            void shouldConvertCurrency() {
                assertEquals(Currency.USD,
                        new Price(10, Currency.USD).minus(new Price(7, Currency.EUR)).currency
                );
            }

            @Test
            @DisplayName("should subtract the given amount")
            void shouldAddAmount() {
                assertEquals(new Price(2.05, Currency.USD),
                        new Price(10, Currency.USD).minus(new Price(7, Currency.EUR))
                );
            }
        }
    }

    @Nested
    @DisplayName("method 'multiply(Price)'")
    class MultiplyMethod {

        @Test
        @DisplayName("should multiply the given amount")
        void shouldMultiplyAmount() {
            assertEquals(new Price(10, Currency.USD),
                    new Price(5, Currency.USD).multiply(2)
            );
        }
    }

    @Nested
    @DisplayName("method 'compareTo(Price)'")
    class CompareToMethod {
        @Test
        @DisplayName("should return anything < 0 if given price is higher")
        void shouldCompareWithSuperior() {
            assertThat(new Price(1.13, Currency.USD))
                    .isLessThan(new Price(1, Currency.EUR));
        }

        @Test
        @DisplayName("should return anything > 0 if given price is lower")
        void shouldCompareWithInferior() {
            assertThat(new Price(1.15, Currency.USD))
                    .isGreaterThan(new Price(1, Currency.EUR));
        }

        @Test
        @DisplayName("should return 0 if given price is of same value")
        void shouldCompareWithEqual() {
            assertThat(0)
                    .isEqualTo(new Price(1.14, Currency.USD)
                            .compareTo(new Price(1, Currency.EUR)));
        }
    }

}

