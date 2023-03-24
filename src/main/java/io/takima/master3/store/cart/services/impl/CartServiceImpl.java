package io.takima.master3.store.cart.services.impl;

import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.cart.services.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartDao cartDao;
    private final CustomerDao customerDao;

    @Override
    public Cart getForCustomer(long customerId){
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", customerId)));

        // get cart by customer id
        Optional<Cart> cart = cartDao.getForCustomer(customer);
        if (cart.isPresent()) {
            return cart.get();
        }
        else {
            Cart newCart = new Cart(LocalDateTime.now(), customer);
            customer.setCart(newCart);
            cartDao.create(newCart);
            cart = cartDao.getForCustomer(customer);
            return cart.get();
        }
    };

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
