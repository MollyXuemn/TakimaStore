package io.takima.master3.store.cart.persistence.impl;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.customer.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class JpaCartDao implements CartDao {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    public JpaCartDao(EntityManager em) {
        this.em = em;
    }
    public Optional<Cart> findById(long id){
        return Optional.ofNullable(em.find(Cart.class, id));
    };
    /**
     * Get the cart for the given customer.
     * If the cart does not exist, create the cart on the fly.
     * @param customerId the id of customer to find the cart for.
     * @return the existing cart , if found, or a newly created cart.
     */
    public Optional<Cart> getForCustomer(Customer customer){
        Cart cart = customer.getCart();
        if (cart == null) {
            return Optional.empty();
        }
        return findById(cart.getId());

    };

    public Cart create(Cart cart) {
        em.persist(cart);  //Make an instance managed and persistent.
        return cart;
    }

    public Cart update(Cart cart) {
        Long cartId = cart.getId();
        Optional<Cart> optionalCart = findById(cartId);
        optionalCart.ifPresentOrElse(c -> em.merge(cart), //the managed instance that the state was merged to
                () -> { throw new NoSuchElementException(String.format("No cart with id: %d.", cartId)); });
        return cart;
    }
}
