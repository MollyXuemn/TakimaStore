package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.domain.Article;
import io.takima.master3.store.domain.Seller;
import io.takima.master3.store.ConnectionManager;
import io.takima.master3.store.mapper.ResultSetMapper;
import io.takima.master3.store.mapper.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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