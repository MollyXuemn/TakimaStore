package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.seller.models.Seller;

import java.sql.SQLException;

import java.util.*;

 public interface SellerDao {

    List<Seller> findAll();
    List<Seller> findByName(String name);
    Optional<Seller> findById(long id);
    Seller update(Seller seller);
    Seller create(Seller seller) ;
    void delete(long id) throws SQLException ;
}