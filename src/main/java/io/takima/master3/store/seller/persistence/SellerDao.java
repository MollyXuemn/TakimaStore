package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.domain.Seller;

import java.sql.SQLException;

import java.util.*;

 public interface SellerDao {

    List<Seller> findAll();


    List<Seller> findByName(String name);


    Optional<Seller> findById(long id);

    void update(Seller seller);

     void create(Seller seller) ;

     void delete(long id) throws SQLException ;
}