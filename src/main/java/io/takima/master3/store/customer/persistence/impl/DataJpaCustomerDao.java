package io.takima.master3.store.customer.persistence.impl;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.persistence.CustomerDao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface DataJpaCustomerDao extends CustomerDao, JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    //Optional<Customer> findById(long id);

    default Page<Customer> findPage(PageSearch page) {
        return findAll(page.getSearch(), page);
    }
    default long count(PageSearch page) {
        return count(page.getSearch());
    }


}
