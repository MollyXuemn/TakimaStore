package io.takima.master3.store.cart.persistence;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.customer.models.Customer;

import java.util.Optional;

public interface CartDao {
    /**
     * Find a cart by its ID
     *
     * @param id the id of cart to findById
     * @return the cart with the searched id.
     */
    Optional<Cart> findById(long id);

    Optional<Cart> getForCustomer(Customer customer);

    void create(Cart cart);

    void update(Cart cart);
}
