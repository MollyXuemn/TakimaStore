package io.takima.master3.store.money;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class ExchangeRateProvider {

    private final Map<String, Double> CURRENCY_RATES;

    public ExchangeRateProvider() {
        CURRENCY_RATES = Map.of(
                "USD", this.getRandomInRange(0.80, 1.20),
                "EUR", this.getRandomInRange(0.60, 1.00),
                "JPY", this.getRandomInRange(1.00, 1.60),
                "RUB", this.getRandomInRange(0.010, 0.100),
                "GBP", this.getRandomInRange(0.40, 0.80));
    }

    public double getExchangeRateForCurrency(String currency) {
        return Optional.ofNullable(CURRENCY_RATES.get(currency))
                .orElseThrow(() -> new IllegalArgumentException("Unknown currency: " + currency));
    }

    private double getRandomInRange(double min, double max) {
        Random random = new Random();
        return (random.nextInt((int) ((max - min) * 100 + 1)) + min * 100) / 100.0;
    }
}
