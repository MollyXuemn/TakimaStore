package io.takima.master3.store.discount.models;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.core.models.Price;

import java.util.HashSet;
import java.util.Map;

public class DiscountedCart extends Cart implements Discount {

    private Price discountedTotal;

    public DiscountedCart(Cart cart, Price discountedTotal) {
        super(cart);

        this.discountedTotal = discountedTotal;
    }

    public DiscountedCart(Cart cart) {
        this(cart, cart.getTotal());
    }

    /**
     *
     * @return the discounted total.
     */
    public Price getTotal() {
        return discountedTotal;
    }

    /**
     * Get the total this cart had before any discount was applied to it.
     * @return the total without discounts.
     */
    public Price getOriginalTotal() {
        return super.getTotal();
    }

    /**
     * Same as {@link #getOriginalTotal()}
     * @return the original total
     */
    @Override
    public Price getOriginalPrice() {
        return getOriginalTotal();
    }

    /**
     * Apply an offer to this cart.
     * If the offer applies to some Articles ({@link Offer#getArticles()} not empty),
     * convert the {@link Article} of this cart to {@link DiscountedArticle}
     * @param offer the offer to apply.
     */
    @Override
    public synchronized void applyOffer(Offer offer) {
        super.getOffers().add(offer);


        Map<Article, Integer> articles = getArticles();

        // if no article selection => apply to the whole cart
        if (offer.getArticles().isEmpty()) {
            this.discountedTotal = offer.apply(this.discountedTotal);
        } else {

            Price oldTotal = getOriginalTotal();

            offer.getArticles().stream()
                    .filter(articles::containsKey)
                    .forEach(a -> {
                        // convert articles to discountedArticles, that have a discounted price
                        DiscountedArticle discountedArticle = new DiscountedArticle(a);
                        discountedArticle.applyOffer(offer);

                        // remove the old article
                        int quantity = articles.get(a);
                        articles.remove(a);

                        // put back the discounted article. (and yes, a bug is sneaking around here)
                        articles.put(a, quantity);
                    });
            Price newTotal = getOriginalTotal();

            this.discountedTotal = discountedTotal.minus(oldTotal).plus(newTotal);
        }
    }

    /**
     * Reset the discounted total to its original value.
     * After this call, getTotal() = getOriginalTotal()
     */
    public void resetTotal() {
        this.discountedTotal = getOriginalTotal();
    }

    public void resetOffers() {
        resetTotal();
        setOffers(new HashSet<>());
    }
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DiscountedCart)) return false;
        final DiscountedCart other = (DiscountedCart) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        final Object this$discountedTotal = this.discountedTotal;
        final Object other$discountedTotal = other.discountedTotal;
        if (this$discountedTotal == null ? other$discountedTotal != null : !this$discountedTotal.equals(other$discountedTotal))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + super.hashCode();
        final Object $discountedTotal = this.discountedTotal;
        result = result * PRIME + ($discountedTotal == null ? 43 : $discountedTotal.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DiscountedCart;
    }
}
