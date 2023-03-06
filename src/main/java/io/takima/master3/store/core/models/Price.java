package io.takima.master3.store.core.models;

import io.takima.master3.store.article.models.Currency;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
@Getter
public class Price {
    @Column
    public final Currency currency;
    @Column(name = "price")
    public final double amount;

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
}

