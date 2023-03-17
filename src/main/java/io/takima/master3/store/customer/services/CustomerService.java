package io.takima.master3.store.customer.services;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findById(long id);

    Page<Customer> findPage(PageSearch pageSearch);
    long count(PageSearch<Customer> pageSearch);

    void deleteById(long customerId);

    Customer create(Customer customer);

    Customer update(Customer customer);
}
