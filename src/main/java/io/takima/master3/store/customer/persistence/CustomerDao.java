package io.takima.master3.store.customer.persistence;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    /**
     * Find a customer by its ID
     *
     * @param id the id of customer to findById
     * @return the customer with the searched id.
     */
    Optional<Customer> findById(long id);

    /**
     * Get a page of Customer, searched by the given page-search.
     *
     * @param pageSearch the page-search. Searches for a given specification in a given order with a given limit and a given offset.
     * @return the page of all customers that matches the given search
     */
    Page<Customer> findAll(PageSearch<Customer> pageSearch);

    /**
     * Count the number customers that matches the search term.
     *
     * @param pageSearch the search term. Searches for both first name and last name, case unsensitive.
     * @return the number of element that matches the search term.
     */
    long count(PageSearch pageSearch);

    /**
     * Save a customer
     *
     * @param customer the customer to save
     */
    Customer save(Customer customer);

    /**
     * Delete a customer by id
     *
     * @param id the id of customer to delete
     */
    void deleteById(long id);
}
