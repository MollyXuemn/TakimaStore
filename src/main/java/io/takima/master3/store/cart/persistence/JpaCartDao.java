package io.takima.master3.store.cart.persistence;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.customer.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCartDao implements CartDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cart> findById(long id) {
        return Optional.ofNullable(entityManager.find(Cart.class, id));
    }

    @Override
    public Optional<Cart> getForCustomer(Customer customer) {
        String QUERY = "SELECT DISTINCT c " +
                "FROM Cart c " +
                "JOIN FETCH c.customer " +
                "LEFT OUTER JOIN FETCH c.cartArticles a " +
                "WHERE c.customer = :customer";

        return entityManager.createQuery(QUERY, Cart.class)
                .setParameter("customer", customer)
                .getResultList()
                .stream().findFirst();
    }


    @Override
    @Transactional
    public void create(Cart cart) {
        entityManager.persist(cart);
    }

    @Override
    @Transactional
    public void update(Cart cart) {
        entityManager.merge(cart);
    }
}
