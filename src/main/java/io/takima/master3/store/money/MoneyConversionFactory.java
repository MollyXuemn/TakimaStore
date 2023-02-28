package io.takima.master3.store.money;


import org.springframework.stereotype.Service;

@Service
public class MoneyConversionFactory {
    public static MoneyConversion getCurrencyConversion(String currency) {
        return new MoneyConversion(currency, new ExchangeRateProvider());
    }
    public static String getCurrency(String country) {
        return new CurrencyByCountryProvider().getCurrencyByCountry(country);
    }

}
