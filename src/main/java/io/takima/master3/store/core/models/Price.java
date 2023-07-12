package io.takima.master3.store.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


@Embeddable
public class Price implements Comparable<Price>{
    @Column
    @Enumerated(EnumType.STRING)
    @NotBlank
    public Currency currency;
    @Column(name = "price")
    @NotNull
    public double amount;

    public Price() {

    }

    public Price(double amount) {
        this.amount = amount;
        this.currency = Currency.USD;
    }

    public Price(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price(double amount, String currency) {
        this.amount = amount;
        this.currency = Currency.fromSymbol(currency);
    }

    public Price convertTo(Currency currency) {
        if (this.currency.equals(currency)) {
            return this;
        }

        return new Price(BigDecimal.valueOf((amount / this.currency.rate) * currency.rate)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue(), currency);
    }

    public Price plus(Price price) {
        return new Price(BigDecimal.valueOf(amount + price.convertTo(currency).amount)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue(), currency);
    }

    public Price minus(Price price) {
        return new Price(BigDecimal.valueOf(amount - price.convertTo(currency).amount)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue(), currency);
    }

    public Price multiply(double ratio) {
        return new Price(BigDecimal.valueOf(amount * ratio)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue(), currency);
    }

    public int compareTo(Price price){
        return Double.compare(amount, price.convertTo(currency).amount);
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.amount, amount) == 0 && currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return currency.symbol + amount;
    }
}

