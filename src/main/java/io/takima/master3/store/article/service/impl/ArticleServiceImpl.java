package io.takima.master3.store.article.service.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.money.MoneyConversion;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.money.MoneyConversionFactory;
import io.takima.master3.store.seller.service.impl.SellerServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleDao articleDao;
    SellerServiceImpl sellerService;
    public List<Article> findAll(){
        return articleDao.findAll();
    };
    public List<Article> findByName(String name){
        return articleDao.findByName(name);
    }
   /* public Optional<Article> findById(long id){
        return articleDao.findById(id);
    };
*/
    public List<Article> findBySellerId(long sellerId) {
        return articleDao.findBySellerId(sellerId);
    };
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
    public Article findById(long articleId) {
        return articleDao.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", articleId)));
    };

    private Article changePrice(Article article){
        Currency currency = article.getPrice().currency;
        Price money = new Price(article.getPrice().amount, currency);
        Seller seller = sellerService.findById(article.getSeller().getId()).orElseThrow(() -> new NoSuchElementException("seller not found"));

        String sellerCurrency = MoneyConversionFactory.getCurrency(seller.getAddress().country);
        MoneyConversion moneyconversion = MoneyConversionFactory.getCurrencyConversion(Currency.valueOf(sellerCurrency));
        Price newPrice = moneyconversion.convert(money);

        return Article.builder()
                .id(article.getId())
                .seller(article.getSeller())
                .product(article.getProduct())
                .availableQuantity(article.getAvailableQuantity())
                .price(newPrice)
                .build();
    }

}
