package io.takima.master3.store.cart.services.impl;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final CustomerDao customerDao;

    @Autowired
    CartServiceImpl(CustomerDao customerDao, CartDao cartDao) {
        this.customerDao = customerDao;
        this.cartDao = cartDao;
    }

    @Override
    public Cart getForCustomer(long customerId) {
        Customer customerToFind = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", customerId)));

        Cart cartToFind = customerToFind.getCart();

        if (cartToFind == null){
            cartToFind = new Cart();
            cartToFind.setCustomer(customerToFind);
            cartDao.create(cartToFind);
        }

        return cartToFind;
    }

    @Override
    public Optional<Cart> findById(long id) {
        return cartDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Cart cart) {
        cartDao.update(cart);
    }
}
