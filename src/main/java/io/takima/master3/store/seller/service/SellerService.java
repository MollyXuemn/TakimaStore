package io.takima.master3.store.seller.service;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Page<Seller> findAll(PageSearch pageSearch);

    Page<Seller> findByName(String name);
    Optional<Seller> findById(long id);
    void update(Seller seller);
    void create(Seller seller);
    void deleteById(long id);

}
