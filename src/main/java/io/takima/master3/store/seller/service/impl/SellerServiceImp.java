package io.takima.master3.store.seller.service.impl;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import io.takima.master3.store.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImp implements SellerService {
    private SellerDao sellerDao;

    @Autowired
    public SellerServiceImp(SellerDao sellerDao) {
        this.sellerDao = sellerDao;
    }

    @Override
    public Optional<Seller> findById(long id) {
        return sellerDao.findById(id);
    }

    @Override
    public Page<Seller> findAll(PageSearch<Seller> pageSearch) {
        return sellerDao.findAll(pageSearch);
    }

    @Override
    public List<Seller> findByName(String name) {
        return sellerDao.findAll(PageSearch.<Seller>builder()
                .search(SearchSpecification.parse("name=" + name))
                .build()).getContent();
    }

    @Override
    public void update(Seller seller) {
        sellerDao.save(seller);
    }

    @Override
    public void create(Seller seller) {
        sellerDao.save(seller);
    }

    @Override
    public void deleteById(long id) {
        sellerDao.deleteById(id);
    }
}
