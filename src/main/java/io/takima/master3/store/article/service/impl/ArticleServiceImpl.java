package io.takima.master3.store.article.service.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.core.pagination.PageResponse;
import io.takima.master3.store.core.pagination.PageSearch;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleDao articleDao;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public PageResponse<Article> findAll(PageSearch pageSearch){
        return articleDao.findAll(pageSearch);
    };
    public List<Article> findByName(String name){
        return articleDao.findByName(name);
    }
    public PageResponse<Article> findBySellerId(long sellerId) {
        return articleDao.findBySellerId(sellerId);
    };
    public long count(PageSearch<Article> pageSearch){return articleDao.count(pageSearch);};
    @Override
    @Transactional
    public void update(Article article){
        articleDao.update(article);
    };
    @Override
    @Transactional
    public void create(Article article){
        articleDao.create(article);
    };
    @Transactional
    public void delete(long id) throws SQLException {
        articleDao.delete(id);
    };
    public Article findById(long id) {
        return articleDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", id)));
    };

    private Article changePrice(Article article){

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
