package io.takima.master3.store.seller.service;
import io.takima.master3.store.domain.Article;
import io.takima.master3.store.domain.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import io.takima.master3.store.seller.persistence.JdbcSellerDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public enum SellerServiceImpl implements SellerService {
    INSTANCE;

    private SellerDao sellerDao = JdbcSellerDao.INSTANCE;

    List<Article> newSellerss = new ArrayList<>();
    SellerServiceImpl() {
        this.sellerDao = JdbcSellerDao.INSTANCE;
    }

    public List<Seller> findAll() {

        return SellerDao.findAll();
    }
    public List<Seller> findByName(String name){
        return SellerDao.findByName(name);
    };
    @Override
    public Optional<Seller> findById(long id) {

        return SellerDao.findById(id);
    }

    public void update(Seller seller){
        SellerDao.update(seller);
    }
    public void create(Seller seller){
        SellerDao.create(seller);
    }

    public void delete(long id) throws SQLException {
        SellerDao.delete(id);
    }


}