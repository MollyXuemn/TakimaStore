package io.takima.master3.store.money;

import java.util.Map;


public class CurrencyByCountryProvider {
    private  final Map<String, String > CURRENCY;
    public CurrencyByCountryProvider() {
        CURRENCY = Map.of(
                "USD","USD" ,
                "ES", "EUR",
                "DE", "EUR",
                "IT", "EUR",
                "JPN", "JPY",
                "RU", "RUB",
                "UK", "GBP",
                "GB", "GBP");
    }
    public String getCurrencyByCountry(String country) {
        return CURRENCY.getOrDefault(country, "USD");
    }

}
