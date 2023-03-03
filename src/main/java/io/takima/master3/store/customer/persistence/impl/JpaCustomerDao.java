package io.takima.master3.store.customer.persistence.impl;

import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Repository
public class JpaCustomerDao implements CustomerDao  {
    @PersistenceContext
    private EntityManager em;
/*    @Autowired
    public JpaCustomerDao(EntityManager em) {
        this.em = em;
    }*/

    @Override
    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(em.find(Customer.class, id)); //Search for an entity of the specified class and primary key
    }
    @Override
    public Customer create(Customer customer) {
        em.persist(customer);  //Make an instance managed and persistent.
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Long customerId = customer.getId();
        Optional<Customer> optionalCustomer = findById(customerId);
        optionalCustomer.ifPresentOrElse(c -> em.merge(customer), //the managed instance that the state was merged to
                () -> { throw new NoSuchElementException(String.format("No customer with id: %d.", customerId)); });
        return customer;
    }

    /**
     * Get a page of Customer, searched by the given search term.
     * @param search the search term. Searches for both first name and last name, case unsensitive.
     * @param limit the maximum amount of result to return
     * @param offset the search offset
     * @return the list of all customers that matches the given search
     */
    @Override
    public List<Customer> findPage(String search, int limit, int offset){
        return em.createQuery(
                "SELECT c FROM Customer c WHERE UPPER(CONCAT(c.firstName, c.lastName)) LIKE UPPER(CONCAT('%', :name, '%'))", Customer.class)
                .setParameter("name",search)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    /**
     * @see CustomerDao#findPage(java.lang.String, int, int)
     */
    @Override
    public List<Customer> findPage(String search) {
        return findPage(search, Integer.MAX_VALUE, 0);
    }

    /**
     * @see CustomerDao#findPage(java.lang.String, int, int)
     */
    @Override
    public List<Customer> findPage(String search, int offset) {
        return findPage(search, Integer.MAX_VALUE, offset);
    }

    /**
     * @see CustomerDao#findPage(java.lang.String, int, int)
     */
    @Override
    public List<Customer> findPage(int offset) {
        return findPage("", Integer.MAX_VALUE, offset);
    }

    /**
     * @see CustomerDao#findPage(java.lang.String, int, int)
     */
    @Override
    public List<Customer> findPage(int limit, int offset) {
        return findPage("", limit, offset);
    }

    /**
     * Count the number customers that matches the search term.
     * @param search the search term. Searches for both first name and last name, case unsensitive.
     * @return the number of element that matches the search term. ??
     */
    @Transactional
    @Override
    public long count(String search){
        search = ("%" + search + "%").toUpperCase();
        return em.createQuery(
                        "SELECT COUNT(c) FROM Customer c WHERE UPPER(CONCAT(c.firstName, c.lastName)) LIKE UPPER(CONCAT('%', :name, '%'))", Long.class) //???
                .setParameter("name", search)
                .getSingleResult();
    }
    @Transactional
    @Override
    public long count() {
        return count("");
    }

    /**
     * Delete a customer by id
     * @param id the id of customer to delete
     */
    @Transactional
    @Override
    public void deleteById(Long id){
        em.remove(findById(id).orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", id))));

    };
}
