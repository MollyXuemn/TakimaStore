package io.takima.master3.store.cart.services.impl;

import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.cart.services.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartDao cartDao;
    private final CustomerDao customerDao;

    @Override
    @Transactional
    public Cart getForCustomer(long customerId){
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", customerId)));

        // get cart by customer id
        return cartDao.getForCustomer(customer).orElseGet(() -> {
            // get customer the cart belongs to
            // if customer has no cart, assign a cart to it.
            Cart c = new Cart();
            customer.setCart(c);
            customerDao.update(customer);
            return c;
        });

    };
    @Transactional
    @Override
    public Cart findById(long id) {
        return cartDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no cart with id %d", id)));
    }

    /**
     * Persist the cart in database
     * @param cart the cart to persist
     */
    @Transactional
    public void save(Cart cart) {
        if (cart.getId() != null) {
            cartDao.update(cart);
        } else {
            cartDao.create(cart);
        }
    }

}
