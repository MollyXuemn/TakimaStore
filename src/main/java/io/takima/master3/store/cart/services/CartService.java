package io.takima.master3.store.cart.services;

import io.takima.master3.store.cart.models.Cart;
import jakarta.transaction.Transactional;

public interface CartService {

     Cart getForCustomer(long customerId);

    /**
     * Get a cart by its ID.
     * @param id the id of the cart to fetch
     * @return the cart with this ID.
     */
     Cart findById(long id) ;

    /**
     * Persist the cart in database
     * @param cart the cart to persist
     */
    @Transactional
     void save(Cart cart);

}
