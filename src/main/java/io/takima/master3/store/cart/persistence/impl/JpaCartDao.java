package io.takima.master3.store.cart.persistence.impl;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.customer.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCartDao implements CartDao {
    @PersistenceContext
    private EntityManager em;
    public Optional<Cart> findById(long id){
        return Optional.ofNullable(em.find(Cart.class, id));
    };

    public Optional<Cart> getForCustomer(Customer customer){

    };

    public void create(Cart cart) {

    }

    public void update(Cart cart) {

    }
}
