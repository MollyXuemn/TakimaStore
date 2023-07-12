package io.takima.master3.store.customer.service;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findById(long id);

    Page<Customer> findAll(PageSearch<Customer> pageSearch);

    List<Customer> findByName(String name);

    void create(Customer customer);

    void update(Customer customer);

    void deleteById(long customerId);
}
