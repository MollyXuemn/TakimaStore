package io.takima.master3.store.article.service;

import io.takima.master3.store.article.persistence.JdbcArticleDao;
import io.takima.master3.store.domain.Article;
import io.takima.master3.store.money.Money;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.money.MoneyConversionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ArticleServiceImpl implements ArticleService {
    INSTANCE;
    private ArticleDao articleDao = JdbcArticleDao.INSTANCE;

    List<Article> newArticles = new ArrayList<>();
    ArticleServiceImpl() {
        this.articleDao = JdbcArticleDao.INSTANCE;
    }

    @Override
    public List<Article> findAll() {
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
            System.err.println(article.toString());
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

        String sellerCurrency = MoneyConversionFactory.getCurrency(article.seller().country());
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
