package io.takima.master3.store.seller.service.impl;

import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import io.takima.master3.store.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
public class SellerServiceImpl implements SellerService {
    private SellerDao sellerDao;
    @Autowired
    public SellerServiceImpl(SellerDao sellerDao) {
        this.sellerDao = sellerDao;
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