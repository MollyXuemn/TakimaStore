package io.takima.master3.store.money;

import io.takima.master3.store.money.ExchangeRateProvider;

public enum MoneyConversionFactory {
    INSTANCE;

    public static MoneyConversion getCurrencyConversion(String currency) {
        return new MoneyConversion(currency, new ExchangeRateProvider());
    }
    public static String getCurrency(String country) {
        return new CurrencyByCountryProvider().getCurrencyByCountry(country);
    }

}
