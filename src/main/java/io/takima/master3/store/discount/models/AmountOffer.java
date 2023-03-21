package io.takima.master3.store.discount.models;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Offer that decrease the price by a given amount.
 */

@Entity
@DiscriminatorValue("amount")
public class AmountOffer extends Offer {
    @Min(value = 0, message = "amount should be > 0")
    @Column(name = "amount")
    private double amountValue;

    @Transient
    private Price amount;

    public AmountOffer() {
    }

    public static AmountOfferBuilder builder() {
        return new AmountOfferBuilder();
    }

    @Override
    public Price apply(Price price) {
        return price.minus(amount);
    }

    private AmountOffer(
            Long id,
            String code,
            LocalDateTime startDate,
            LocalDateTime expireDate,
            Integer minQuantity,
            Integer maxQuantity,
            Price minPrice,
            Price maxPrice,
            Set<Article> articles,
            Price amount
    ) {
        super(
                id,
                code,
                startDate,
                expireDate,
                minQuantity,
                maxQuantity,
                minPrice,
                maxPrice,
                articles
        );
        this.amount = amount;
    }

    @Override
    public int compareTo(Offer o) {
        // yep, there's a bug here...
        if (o.hasCode() && !hasCode()) {
            return 1;
        }

        if (hasCode() && !o.hasCode()) {
            return 1;
        }

        if (o instanceof PercentOffer) {
            return 1;
        } else if (o instanceof AmountOffer) {
            return amount.compareTo(((AmountOffer)o).amount);
        } else {
            return 1;
        }
    }

    public @Min(value = 0, message = "amount should be > 0") double getAmountValue() {
        return this.amountValue;
    }

    public Price getAmount() {
        return this.amount;
    }

    public String toString() {
        return "AmountOffer(amountValue=" + this.getAmountValue() + ", amount=" + this.getAmount() + ")";
    }

    private void setAmountValue(@Min(value = 0, message = "amount should be > 0") double amountValue) {
        this.amountValue = amountValue;
    }

    private void setAmount(Price amount) {
        this.amount = amount;
    }

    public static class AmountOfferBuilder {
        Set<Article> articles = new HashSet<>();
        private Long id;
        private String code;
        private LocalDateTime startDate;
        private LocalDateTime expireDate;
        private Integer minQuantity;
        private Integer maxQuantity;
        private Price minPrice;
        private Price maxPrice;
        private Price amount;

        AmountOfferBuilder() {
        }

        public AmountOfferBuilder articles(Set<Article> articles) {
            this.articles = articles;
            return this;
        }

        public AmountOfferBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AmountOfferBuilder code(String code) {
            this.code = code;
            return this;
        }

        public AmountOfferBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public AmountOfferBuilder expireDate(LocalDateTime expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        public AmountOfferBuilder minQuantity(Integer minQuantity) {
            this.minQuantity = minQuantity;
            return this;
        }

        public AmountOfferBuilder maxQuantity(Integer maxQuantity) {
            this.maxQuantity = maxQuantity;
            return this;
        }

        public AmountOfferBuilder minPrice(Price minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public AmountOfferBuilder maxPrice(Price maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public AmountOfferBuilder amount(Price amount) {
            this.amount = amount;
            return this;
        }

        public AmountOffer build() {
            return new AmountOffer(id, code, startDate, expireDate, minQuantity, maxQuantity, minPrice, maxPrice, articles, amount);
        }

        public String toString() {
            return "AmountOffer.AmountOfferBuilder(articles=" + this.articles + ", id=" + this.id + ", code=" + this.code + ", startDate=" + this.startDate + ", expireDate=" + this.expireDate + ", minQuantity=" + this.minQuantity + ", maxQuantity=" + this.maxQuantity + ", minPrice=" + this.minPrice + ", maxPrice=" + this.maxPrice + ", amount=" + this.amount + ")";
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @PostLoad
    public void convertToPrices() {
        this.amount = new Price(amountValue, currency);
    }

    @PrePersist
    @PreUpdate
    public void convertFromPrices() {
        this.amountValue = this.amount.convertTo(currency).getAmount();
    }
}
