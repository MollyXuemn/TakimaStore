package io.takima.master3.store.seller.service;

import io.takima.master3.store.seller.models.Seller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SellerService {
    List<Seller> findAll();

    List<Seller> findByName(String name);
    Optional<Seller> findById(long id);
    void update(Seller seller);
    void create(Seller seller);
    void delete(long id) throws SQLException;

}
