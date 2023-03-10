package io.takima.master3.store.cart.services;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.article.models.Article;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface CartService {

    public Cart getForCustomer(long customerId);

    /**
     * Get a cart by its ID.
     * @param id the id of the cart to fetch
     * @return the cart with this ID.
     */
    public Cart findById(long id) ;

    /**
     * Persist the cart in database
     * @param cart the cart to persist
     */
    @Transactional
    public void save(Cart cart);

}
