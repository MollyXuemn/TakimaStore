package io.takima.master3.store.cart.services;

import io.takima.master3.store.cart.models.Cart;

import java.util.Optional;

public interface CartService {

    /**
     * Get the cart for the given customer.
     * If the cart does not exist, create the cart on the fly.
     * @param customerId the id of customer to find the cart for.
     * @return the existing cart , if found, or a newly created cart.
     */
    Cart getForCustomer(long customerId);

    /**
     * Get a cart by its ID.
     * @param id the id of the cart to fetch
     * @return the cart with this ID.
     */
    Optional<Cart> findById(long id);

    /**
     * Persist the cart in database
     * @param cart the cart to persist
     */
    void save(Cart cart);
}
