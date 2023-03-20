package io.takima.master3.store.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import java.math.BigDecimal;
import java.math.RoundingMode;


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
         double roundedAmount = BigDecimal.valueOf((amount * 1.0 / this.currency.rate) * currency.rate)
                    .setScale(2,RoundingMode.HALF_EVEN)
                    .doubleValue();
        return new Price(roundedAmount, currency);
    }

    public int compareTo(Price price){
        return Double.compare(this.getAmount(), price.convertTo(this.currency).getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (Double.compare(price.amount, amount) != 0) return false;
        return currency == price.currency;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = currency != null ? currency.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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


