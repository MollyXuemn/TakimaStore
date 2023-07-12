package io.takima.master3.store.customer.service.impl;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import io.takima.master3.store.customer.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    @Autowired
    CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Optional<Customer> findById(long id) {
        return customerDao.findById(id);
    }

    @Override
    public Page<Customer> findAll(PageSearch<Customer> pageSearch) {
        return customerDao.findAll(pageSearch);
    }

    @Override
    public List<Customer> findByName(String name) {
        return customerDao.findAll(PageSearch.<Customer>builder()
                .search(SearchSpecification.parse("lastname=" + name))
                .build()).getContent();
    }

    @Override
    @Transactional
    public void create(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    @Transactional
    public void update(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(long customerId) {
        customerDao.deleteById(customerId);
    }
}
