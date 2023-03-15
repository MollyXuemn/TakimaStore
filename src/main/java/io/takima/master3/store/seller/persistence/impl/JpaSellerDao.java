package io.takima.master3.store.seller.persistence.impl;

import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class JpaSellerDao implements SellerDao {
    @PersistenceContext
    private EntityManager em;
    public List<Seller> findAll() {
        return em.createQuery("SELECT a FROM Seller a", Seller.class).getResultList();
    }
    public List<Seller> findByName(String name){
        return em.createQuery("SELECT a FROM Seller a WHERE UPPER(a.name) LIKE UPPER(CONCAT('%', :name, '%')) ", Seller.class)
                .setParameter("name",name.toUpperCase())
                .getResultList();
    }

    public Optional<Seller> findById(long id) {
        return Optional.ofNullable(em.find(Seller.class, id));
    }

    public Seller update(Seller seller){
        Long sellerId = seller.getId();
        Optional<Seller> optionalSeller = findById(sellerId);
        optionalSeller.ifPresentOrElse(c -> em.merge(seller), //the managed instance that the state was merged to
                () -> { throw new NoSuchElementException(String.format("No customer with id: %d.", sellerId)); });
        return seller;
    }

    public Seller create(Seller seller){
        em.persist(seller);
        return seller;
    }

    public void delete(long id) throws SQLException {
        em.remove(findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("no seller with id %d", id))));
    };
}
