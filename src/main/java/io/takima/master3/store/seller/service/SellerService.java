package io.takima.master3.store.seller.service;

import io.takima.master3.store.domain.Seller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SellerService {
    public List<Seller> findAll();

    public List<Seller> findByName(String name);
    public Optional<Seller> findById(long id);
    public void update(Seller seller);
    public void create(Seller seller);
    public void delete(long id) throws SQLException;

}
