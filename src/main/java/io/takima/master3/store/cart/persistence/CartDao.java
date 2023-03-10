package io.takima.master3.store.cart.persistence;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.customer.models.Customer;

import java.util.Optional;

public interface CartDao {
    Optional<Cart> findById(long id);

    Optional<Cart> getForCustomer(Customer customer);

    Cart create(Cart cart);

    Cart update(Cart cart);
}
