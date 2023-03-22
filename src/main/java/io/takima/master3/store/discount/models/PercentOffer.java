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
 * Offer that decrease the price by a given percent.
 */

@Entity
@DiscriminatorValue("percent")
public class PercentOffer extends Offer {

    @Min(value = 0, message = "percent should be between 0 and 100")
    @Max(value = 100, message = "percent should be between 0 and 100")
    private double percent; // 0 > percent > 100

    public PercentOffer() {
    }

    public static PercentOfferBuilder builder() {
        return new PercentOfferBuilder();
    }

    @Override
    public Price apply(Price price) {
        return price.multiply((100 - percent) / 100);
    }

    private PercentOffer(
            Long id,
            String code,
            LocalDateTime startDate,
            LocalDateTime expireDate,
            Integer minQuantity,
            Integer maxQuantity,
            Price minPrice,
            Price maxPrice,
            Set<Article> articles,
            double percent
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
                articles);
        setPercent(percent);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Offer o) {
        // oops, seems there is a mistake here
        if (o.hasCode() && !hasCode()) {
            return -1;
        }

        if (hasCode() && !o.hasCode()) {
            return 1;
        }

        if (o instanceof AmountOffer) {
            return 1;
        } else if (o instanceof PercentOffer) {
            return Double.compare(percent, ((PercentOffer)o).percent);
        } else {
            return -1;
        }
    }

    public void setPercent(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent should be between 0 and 100. Got " + percent);
        }
        this.percent = percent;
    }

    public @Min(value = 0, message = "percent should be between 0 and 100") @Max(value = 100, message = "percent should be between 0 and 100") double getPercent() {
        return this.percent;
    }

    public String toString() {
        return "PercentOffer(percent=" + this.getPercent() + ")";
    }

    public static class PercentOfferBuilder {
        Set<Article> articles = new HashSet<>();
        private Long id;
        private String code;
        private LocalDateTime startDate;
        private LocalDateTime expireDate;
        private Integer minQuantity;
        private Integer maxQuantity;
        private Price minPrice;
        private Price maxPrice;
        private double percent;

        PercentOfferBuilder() {
        }

        public PercentOfferBuilder articles(Set<Article> articles) {
            this.articles = articles;
            return this;
        }

        public PercentOfferBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PercentOfferBuilder code(String code) {
            this.code = code;
            return this;
        }

        public PercentOfferBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public PercentOfferBuilder expireDate(LocalDateTime expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        public PercentOfferBuilder minQuantity(Integer minQuantity) {
            this.minQuantity = minQuantity;
            return this;
        }

        public PercentOfferBuilder maxQuantity(Integer maxQuantity) {
            this.maxQuantity = maxQuantity;
            return this;
        }

        public PercentOfferBuilder minPrice(Price minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public PercentOfferBuilder maxPrice(Price maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public PercentOfferBuilder percent(double percent) {
            this.percent = percent;
            return this;
        }

        public PercentOffer build() {
            return new PercentOffer(id, code, startDate, expireDate, minQuantity, maxQuantity, minPrice, maxPrice, articles, percent);
        }

        public String toString() {
            return "PercentOffer.PercentOfferBuilder(articles=" + this.articles + ", id=" + this.id + ", code=" + this.code + ", startDate=" + this.startDate + ", expireDate=" + this.expireDate + ", minQuantity=" + this.minQuantity + ", maxQuantity=" + this.maxQuantity + ", minPrice=" + this.minPrice + ", maxPrice=" + this.maxPrice + ", percent=" + this.percent + ")";
        }
    }
}
