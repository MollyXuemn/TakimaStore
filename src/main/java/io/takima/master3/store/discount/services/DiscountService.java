package io.takima.master3.store.discount.services;

import io.takima.master3.store.discount.DiscountException;
import io.takima.master3.store.discount.models.Offer;
import io.takima.master3.store.cart.models.Cart;

public interface DiscountService {

    /**
     * Fetches all valid <b>automatic offers</b> for the current date,
     * and applies the offers to the cart when offer's requirements are met.
     * This method is not intended to throw {@link DiscountException}.
     * If no offers found, or the cart does not meets some requirements, silently discard the offers.
     *
     * An automatic offer is an offer where {@link Offer#getCode()} is null or empty,
     * and that is to be applied automatically without any user interaction.
     *
     * Applying an offer to the cart means adding an offer to the list of offers this cart has,
     * and creating a {@link io.takima.master3.ma.discount.models.DiscountedCart} copy for this cart,
     * where the {@link Cart#getTotal()} computes the discounted price.
     *
     * If the cart already comes with some offers saved, this method silently clean up offers that are not valid anymore.
     *
     * This method saves the cart back to the database.
     * @param cart the Cart to apply offers to.
     * @return the {@link io.takima.master3.ma.discount.models.DiscountedCart} with the updated discounted total.
     */
    Cart applyOffers(Cart cart);

    /**
     * Apply all <b>automatic offers</b> the same way {@link DiscountService#applyOffers(Cart)} does, but also try to apply
     * the offer identified by the given code.
     *
     * Contrary to the automatic offers, this method is designed to throw {@link DiscountException}
     * if some of the requirement are not met for the offer with the given code, or if no Offer is found for this code.
     *
     * Possible exceptions are described by {@link DiscountException.Code}
     *
     * @param cart the cart to apply offers to
     * @param code the code for an extra offer to apply
     *
     * @return the {@link io.takima.master3.ma.discount.models.DiscountedCart} with the updated discounted total.
     */
    Cart addOffer(Cart cart, String code) throws DiscountException;

    /**
     * Remove the offer with the given code that was previously applied to the given Cart.
     * Throws NoSuchElementException if offer with the given code has not been applied to the cart beforehand.
     *
     * @param cart the cart to apply offers to
     * @param code the code for the offer to remove
     *
     * @return the {@link io.takima.master3.ma.discount.models.DiscountedCart} with the updated discounted total.
     */
    Cart removeOffer(Cart cart, String code) throws DiscountException;
}
