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

    SellerServiceImpl() {
        this.sellerDao = JdbcSellerDao.INSTANCE;
    }

    public List<Seller> findAll() {
        return sellerDao.findAll();
    }

    public List<Seller> findByName(String name) {
        return sellerDao.findByName(name);
    }

    public Optional<Seller> findById(long id) {
        return sellerDao.findById(id);
    }

    public void update(Seller seller) {
        sellerDao.update(seller);
    }

    public void create(Seller seller) {
        sellerDao.create(seller);
    }

    public void delete(long id) throws SQLException {
        sellerDao.delete(id);
    }
}