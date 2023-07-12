package io.takima.master3.store.seller.service;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Optional<Seller> findById(long id);

    Page<Seller> findAll(PageSearch<Seller> pageSearch);

    List<Seller> findByName(String name);

    void create(Seller seller);

    void update(Seller seller);

    void deleteById(long id);
}
