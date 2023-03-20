package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;

import java.util.*;

 public interface SellerDao {

    Page<Seller> findAll(PageSearch pageSearch);
    Page<Seller> findByName(String name, Pageable pageable);
    Optional<Seller> findById(long id);
    Seller save(Seller seller);

    void deleteById(long id);
}