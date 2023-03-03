package io.takima.master3.store.customer.services.impl;

import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import io.takima.master3.store.customer.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;
    @Transactional
    public Optional<Customer> findById(long id) {
        return customerDao.findById(id);
    }

    @Override
    @Transactional
    public List<Customer> findPage(String search, int limit, int offset) {
        return customerDao.findPage(search, limit, offset);
    }

    @Override
    @Transactional
    public void deleteById(long customerId) {
        customerDao.deleteById(customerId);
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return customerDao.create(customer);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return customerDao.update(customer);
    }
}
