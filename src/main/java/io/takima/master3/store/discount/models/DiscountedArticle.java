package io.takima.master3.store.discount.models;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Price;

/**
 * An article with a discounted price.
 */
public class DiscountedArticle extends Article implements Discount {

    private Price discountedPrice;

    public DiscountedArticle(Article article) {
        super(article);
        this.discountedPrice = article.getPrice();
    }

    public DiscountedArticle(Article article, Price discountedPrice) {
        super(article);
        this.discountedPrice = discountedPrice;
    }

    /**
     *
     * @return the discounted price.
     */
    @Override
    public Price getPrice() {
        return discountedPrice;
    }

    /**
     * Get the total this article had before any discount was applied to it.
     * @return the total without discounts.
     */
    @Override
    public Price getOriginalPrice() {
        return super.getPrice();
    }

    /**
     * Apply an offer to this article, and compute the new discounted price.
     * @param offer the offer to apply.
     */
    public void applyOffer(Offer offer) {
        this.discountedPrice = offer.apply(discountedPrice);
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }

    public int hashCode() {
        return super.hashCode();
    }

    protected boolean canEqual(Object other) {
        return other instanceof DiscountedArticle;
    }
}
