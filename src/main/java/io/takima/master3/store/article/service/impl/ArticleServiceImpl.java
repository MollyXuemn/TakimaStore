package io.takima.master3.store.article.service.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.core.models.Money;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.money.MoneyConversionFactory;
import io.takima.master3.store.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao;
    private final SellerService sellerService;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao, SellerService sellerService
    ) {
        this.articleDao = articleDao;
        this.sellerService = sellerService;
    }

    @Override
    public List<Article> findAll() {
        List<Article> newArticles = new ArrayList<>();
        List<Article> articles = articleDao.findAll();
        for(Article article : articles) {
            newArticles.add(changePrice(article));
        }
        return newArticles;

    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.of(this.changePrice(articleDao.findById(id).orElse(null)));

    }

    @Override
    public List<Article> findByName(String name) {
        List<Article> newArticles = new ArrayList<>();
        List<Article> articles = articleDao.findByName(name);
        for(Article article :  articles){
            newArticles.add(this.changePrice(article));
        }
        return newArticles;

    }
    @Override
    public List<Article> findBySellerId(long sellerId) {
        List<Article> articles = articleDao.findBySellerId(sellerId);
        List<Article> newArticles = new ArrayList<>();
        for(Article article :  articles){
            //System.err.println(article.toString());
            newArticles.add(this.changePrice(article));
        }
        return newArticles;
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Override
    public void create(Article article) {
        articleDao.create(article);
    }

    @Override
    public void delete(long id)  {
        try {
            articleDao.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Article changePrice(Article article){
        Money money = new Money(article.price(),article.currency());

        Seller seller = sellerService.findById(article.seller().id()).orElseThrow(() -> new NoSuchElementException("seller not found"));

        String sellerCurrency = MoneyConversionFactory.getCurrency(seller.country());
        double newPrice = MoneyConversionFactory.getCurrencyConversion(sellerCurrency).convert(money).amount();

        return Article.builder()
                .id(article.id())
                .seller(article.seller())
                .ref(article.ref())
                .name(article.name())
                .description(article.description())
                .image(article.image())
                .availableQuantity(article.availableQuantity())
                .price(newPrice)
                .currency(sellerCurrency)
                .build();
    }

}
