package io.takima.master3.store.customer.services;

import io.takima.master3.store.customer.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findById(long id);

    List<Customer> findPage(String search, int limit, int offset);

    void deleteById(long customerId);

    Customer create(Customer customer);

    Customer update(Customer customer);
}
