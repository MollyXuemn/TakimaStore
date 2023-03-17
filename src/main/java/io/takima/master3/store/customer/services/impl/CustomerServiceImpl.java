package io.takima.master3.store.customer.services.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import io.takima.master3.store.customer.services.CustomerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    @Transactional
    public Optional<Customer> findById(long id) {
        return customerDao.findById(id);
    }

    @Transactional
    public Page<Customer> findPage(PageSearch pageSearch) {
        return customerDao.findPage(pageSearch);
    }
    @Transactional
    public long count(PageSearch<Customer> pageSearch){
        return customerDao.count(pageSearch);
    };
    @Override
    @Transactional
    public void deleteById(long customerId) {
        customerDao.deleteById(customerId);
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return customerDao.save(customer);
    }
}
