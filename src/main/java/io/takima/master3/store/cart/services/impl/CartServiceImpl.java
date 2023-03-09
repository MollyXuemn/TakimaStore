package io.takima.master3.store.cart.services.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.cart.services.CartService;

public class CartServiceImpl implements CartService {
    private final CartDao cartDao;
    public Cart getForCustomer(long customerId){

    };

    /**
     * Get a cart by its ID.
     * @param id the id of the cart to fetch
     * @return the cart with this ID.
     */
    Cart findById(long id);

    /**
     * Persist the cart in database
     * @param cart the cart to persist
     */
    void save(Cart cart);

    Article findById(long articleId);
}
