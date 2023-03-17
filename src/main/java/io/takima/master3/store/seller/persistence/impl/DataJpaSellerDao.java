package io.takima.master3.store.seller.persistence.impl;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface DataJpaSellerDao extends SellerDao, JpaRepository<Seller, Long>, JpaSpecificationExecutor<Seller> {
    Optional<Seller> findById(long id);
    default Page<Seller> findAll(PageSearch pageSearch) {
        return findAll(pageSearch.getSearch(), pageSearch);
    }

    default long count(PageSearch page) {
        return count(page.getSearch());
    }
    Seller save(Seller seller);
    void deleteById(Long id);


}
