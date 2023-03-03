package io.takima.master3.store.customer.persistence;

import io.takima.master3.store.customer.models.Customer;

import java.lang.annotation.Retention;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> findById(long id);

    /**
     * Create a customer
     * @param customer the customer to create
     */
    Customer create(Customer customer);

    /**
     * Update a customer
     * @param customer the customer to update
     */
    Customer update(Customer customer);


    List<Customer> findPage(String search, int limit, int offset);

    default List<Customer> findPage(String search) {
        return findPage(search, Integer.MAX_VALUE, 0);
    }


    default List<Customer> findPage(String search, int offset) {
        return findPage(search, Integer.MAX_VALUE, offset);
    }


    default List<Customer> findPage(int offset) {
        return findPage("", Integer.MAX_VALUE, offset);
    }

    /**
     * @see CustomerDao#findPage(java.lang.String, int, int)
     */
    default List<Customer> findPage(int limit, int offset) {
        return findPage("", limit, offset);
    }

    /**
     * Count the number customers that matches the search term.
     * @param search the search term. Searches for both first name and last name, case unsensitive.
     * @return the number of element that matches the search term.
     */
    long count(String search);

    default long count() {
        return count("");
    }

    /**
     * Delete a customer by id
     * @param id the id of customer to delete
     */
    void deleteById(Long id);
}
