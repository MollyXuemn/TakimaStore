package io.takima.master3.store.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;


@Embeddable
@Getter
public class Price {
    @Convert(converter = Currency.CurrencyConverter.class)
    public Currency currency;
    @Column(name = "price")
    public double amount;

    public Price() {
        this(0.0);
    }

    public Price(double amount) {
        this.amount = amount;
        this.currency = Currency.DOLLAR;
    }

    public Price(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price(double amount, String currency) {
        this.amount = amount;
        this.currency = Currency.fromSymbol(currency);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Price plus(Price price) {
        return new Price(amount + price.convertTo(currency).amount, currency);
    }

    public Price minus(Price price) {
        return new Price(amount - price.convertTo(currency).amount, currency);
    }

    public Price multiply(double ratio) {
        return new Price(amount * ratio, currency);
    }


    public Price convertTo(Currency currency) {
        if (this.currency.equals(currency)) {
            return this;
        }
        return new Price((amount * 1.0 / this.currency.rate) * currency.rate, currency);
    }

    @Override
    public String toString() {
        return currency.symbol + amount;
    }

    public static final class Builder {
        private double amount;
        private Currency currency;

        public Builder() {
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Price build() {
            return new Price(this.amount, this.currency);
        }
    }
}


