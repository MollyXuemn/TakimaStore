package io.takima.master3.store.customer.persistence;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.customer.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataJpaCustomerDao extends CustomerDao, JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    default Page<Customer> findAll(PageSearch<Customer> page) {
        return findAll(page.getSearch(), page);
    }

    default long count(PageSearch page) {
        return count(page.getSearch());
    }
}
