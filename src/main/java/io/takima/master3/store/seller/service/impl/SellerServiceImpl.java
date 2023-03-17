package io.takima.master3.store.seller.service.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import io.takima.master3.store.seller.service.SellerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Seller> findAll(PageSearch pageSearch) {
        return sellerDao.findAll(pageSearch);
    }

    public Page<Seller> findByName(String name) {
        Specification<Seller> spec = (name != null) ? SearchSpecification.parse(name) : Specification.where(null);
        return sellerDao.findAll(new PageSearch
                .Builder<Seller>()
                .search(spec)
                .build());
    }

    public Optional<Seller> findById(long id) {
        return sellerDao.findById(id);
    }
    @Transactional
    public void update(Seller seller) {
        sellerDao.save(seller);
    }
    @Transactional
    public void create(Seller seller) {
        sellerDao.save(seller);
    }
    @Transactional
    public void deleteById(long id) {
        sellerDao.deleteById(id);
    }
}