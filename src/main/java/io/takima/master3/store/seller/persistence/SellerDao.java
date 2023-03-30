package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.ConnectionManager;
import io.takima.master3.store.domain.Seller;
import io.takima.master3.store.exceptions.PersistenceException;
import io.takima.master3.store.mapper.ResultSetMapper;
import io.takima.master3.store.mapper.SellerMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SellerDao {
    List<Seller> findAll() ;
    List<Seller> findByName(String name);


    Optional<Seller> findById(long id) ;
    void create(Seller seller);

    void delete(long id) throws SQLException ;
}