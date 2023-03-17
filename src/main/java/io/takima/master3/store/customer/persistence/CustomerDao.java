package io.takima.master3.store.customer.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> findById(long id);

    /**
     * Create and Update a customer
     * @param customer the customer to create
     */
    Customer save(Customer customer);

    /**
     * Get a page of Customer, searched by the given search term.
     * @param search the search term. Searches for both first name and last name, case unsensitive.
     * @param limit the maximum amount of result to return
     * @param offset the search offset
     * @return the list of all customers that matches the given search
     */
    Page<Customer> findPage(PageSearch page);

    /**
     * Count the number customers that matches the search term.
     * @param search the search term. Searches for both first name and last name, case unsensitive.
     * @return the number of element that matches the search term.
     */
    long count(PageSearch search);
    long count();

    void deleteById(Long id);


}
