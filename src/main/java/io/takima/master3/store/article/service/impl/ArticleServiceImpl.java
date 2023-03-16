package io.takima.master3.store.article.service.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.article.persistence.BySellerSpecification;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleDao articleDao;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public Page<Article> findAll(PageSearch pageSearch) {
        return articleDao.findAll(pageSearch);
    }

    ;

    public List<Article> findByName(String name) {
        return articleDao.findByProductName(name);
    }

    public Page<Article> findAllBySeller(Seller seller) { //need to note
        return articleDao.findAll(new PageSearch
                .Builder<Article>()
                .search(new BySellerSpecification(seller))
                .build());
    };

    public long count(PageSearch<Article> pageSearch) {
        return articleDao.count(pageSearch);
    }

    ;

    @Override
    @Transactional
    public void update(Article article) {
        articleDao.save(article);
    }

    ;

    @Override
    @Transactional
    public void create(Article article) {
        articleDao.save(article);
    }

    ;

    @Transactional
    public void delete(long id) {
        articleDao.deleteById(id);
    }

    ;

    public Article findById(long id) {
        return articleDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", id)));
    }

    ;

    private Article changePrice(Article article) {

        Currency sellerCurrency = article.getSeller().getAddress().getCountry().currency;

        Price newPrice = article.getPrice().convertTo(sellerCurrency);


        return Article.builder()
                .id(article.getId())
                .seller(article.getSeller())
                .product(article.getProduct())
                .availableQuantity(article.getAvailableQuantity())
                .price(newPrice)
                .build();
    }

}
