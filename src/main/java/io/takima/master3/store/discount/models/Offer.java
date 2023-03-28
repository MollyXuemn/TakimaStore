package io.takima.master3.store.discount.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonView(Offer.Views.LIGHT.class)
public abstract class Offer implements Comparable<Offer> {

    @Id
    @JsonView(Views.FULL.class)
    private Long id;
    @JsonView(Views.LIGHT.class)
    private String code;

    // no start limit if null
    @NotNull
    private LocalDateTime startDate;

    // no end limit if null
    @NotNull
    private LocalDateTime expireDate;

    @Column(name = "min_qty")
    @PositiveOrZero
    private Integer minQuantity;

    @Column(name = "max_qty")
    @PositiveOrZero
    private Integer maxQuantity;

    @Transient
    private Price minPrice;

    @Transient
    private Price maxPrice;

    @Column(name = "min_price")
    private Double minPriceValue;

    @Column(name = "max_price")
    private Double maxPriceValue;

    @Column()
    protected Currency currency;

    @ManyToMany()
    @JoinTable(name = "article_offer",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    // Applies on all articles if null
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    public Offer(Long id, String code, LocalDateTime startDate,
                 LocalDateTime expireDate, Integer minQuantity,
                 Integer maxQuantity, Price minPrice, Price maxPrice, Set<Article> articles) {
        this.id = id;
        this.code = code;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.maxQuantity = maxQuantity;
        this.minQuantity= minQuantity;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.articles = articles;
    }

    public Offer() {
    }

    /**
     * Apply this offer on the given price
     *
     * @param price the price to apply offer on
     * @return the discounted price
     */
    public abstract Price apply(Price price);

    /**
     * @return true if this offer is applicable with discount code.
     */
    public boolean hasCode() {
        return code != null;
    }

    /**
     * @return true if this offer applies with an article selection condition
     */
    public boolean hasArticleCriteria() {
        return this.articles != null && !this.articles.isEmpty();
    }


    /**
     *
     * @return true if this offer is constraint by an article quantity condition
     */
    public boolean hasQuantityCriteria() {
        // shoot that bug!
        return this.maxQuantity != null && this.minQuantity != null;
    }

    /**
     *
     * @return true if this offer is constraint by price condition
     */
    public boolean hasPriceCriteria() {
        // shoot that bug!
        return this.maxPrice != null && this.minPrice != null;
    }

    @PostLoad
    public void convertToPrices() {
        if (minPriceValue != null) {
            this.minPrice = new Price(minPriceValue, currency);
        }

        if (maxPriceValue != null) {
            this.maxPrice = new Price(maxPriceValue, currency);
        }
    }

    @PrePersist
    @PreUpdate
    public void convertFromPrices() {
        if (currency == null && this.minPrice != null) {
            currency = minPrice.getCurrency();
        }

        if (currency == null && this.maxPrice != null) {
            currency = maxPrice.getCurrency();
        }

        if (this.minPrice != null) {
            this.minPriceValue = this.minPrice.convertTo(currency).getAmount();
        }

        if (this.maxPrice != null) {
            this.maxPriceValue = this.maxPrice.convertTo(currency).getAmount();
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public @NotNull LocalDateTime getStartDate() {
        return this.startDate;
    }

    public @NotNull LocalDateTime getExpireDate() {
        return this.expireDate;
    }

    public @PositiveOrZero Integer getMinQuantity() {
        return this.minQuantity;
    }

    public @PositiveOrZero Integer getMaxQuantity() {
        return this.maxQuantity;
    }

    public Price getMinPrice() {
        return this.minPrice;
    }

    public Price getMaxPrice() {
        return this.maxPrice;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public Double getMinPriceValue() {
        return minPriceValue;
    }

    public void setMinPriceValue(Double minPriceValue) {
        this.minPriceValue = minPriceValue;
    }

    public Double getMaxPriceValue() {
        return maxPriceValue;
    }

    public void setMaxPriceValue(Double maxPriceValue) {
        this.maxPriceValue = maxPriceValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartDate(@NotNull LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setExpireDate(@NotNull LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public void setMinQuantity(@PositiveOrZero Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setMaxQuantity(@PositiveOrZero Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setMinPrice(Price minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Price maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public String toString() {
        return "Offer(id=" + this.getId() + ", code=" + this.getCode() + ", " +
                "startDate=" + this.getStartDate() +
                ", expireDate=" + this.getExpireDate() +
                ", minQuantity=" + this.getMinQuantity() +
                ", maxQuantity=" + this.getMaxQuantity() +
                ", minPrice=" + this.getMinPrice() +
                ", maxPrice=" + this.getMaxPrice() +
                ", minPriceValue=" + this.getMinPriceValue() +
                ", maxPriceValue=" + this.getMaxPriceValue() +
                ", currency=" + this.getCurrency() +
                ", articles=" + this.getArticles() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Offer)) return false;
        final Offer other = (Offer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Offer;
    }
    public static class Views {
        public interface LIGHT {}
        public interface FULL extends LIGHT {}
    }

}
