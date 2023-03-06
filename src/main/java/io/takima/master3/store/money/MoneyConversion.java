package io.takima.master3.store.money;


import io.takima.master3.store.article.models.Currency;
import io.takima.master3.store.core.models.Price;

public record MoneyConversion(
        Currency currency,
        ExchangeRateProvider exchangeRateProvider) {

    public Price convert(Price price) {
        double targetCurrencyRate = exchangeRateProvider().getExchangeRateForCurrency(String.valueOf(currency));
        double currentCurrencyRate = exchangeRateProvider().getExchangeRateForCurrency(String.valueOf(price.currency));
        return new Price((price.amount / currentCurrencyRate) * targetCurrencyRate, currency);
    }
}
