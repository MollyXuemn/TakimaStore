package io.takima.master3.store.money;


import io.takima.master3.store.core.models.Money;

public record MoneyConversion(
        String currency,
        ExchangeRateProvider exchangeRateProvider) {

    public Money convert(Money price) {
        double targetCurrencyRate = exchangeRateProvider().getExchangeRateForCurrency(currency);
        double currentCurrencyRate = exchangeRateProvider().getExchangeRateForCurrency(price.currency());
        return new Money((price.amount() / currentCurrencyRate) * targetCurrencyRate, currency);
    }
}
