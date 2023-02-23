package io.takima.master3.store;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.article.persistence.JdbcArticleDao;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.article.service.ArticleServiceImpl;
import io.takima.master3.store.seller.persistence.JdbcSellerDao;
import io.takima.master3.store.seller.persistence.SellerDao;
import io.takima.master3.store.seller.service.SellerService;
import io.takima.master3.store.seller.service.SellerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.takima.master3.store.domain.Article;
import io.takima.master3.store.domain.Seller;

public class MainApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
    private final ArticleService articleService = ArticleServiceImpl.INSTANCE;
    private final SellerService sellerService = SellerServiceImpl.INSTANCE;

    public static void main(String[] args) {
        new MainApplication().run();
    }

    public void run() {
        Map<Seller, List<Article>> articlesBySeller;
        List<Seller> sellers = sellerService.findAll();
        articlesBySeller = sellers.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        seller -> articleService.findBySellerId(seller.id())));
        articlesBySeller.forEach((seller, articles) -> {
            LOGGER.info(seller.name());
            articles.forEach(article -> LOGGER.info("\t" +
                    article.name() + " " +
                    article.price() + " " +
                    article.currency()));
        });
    }
}
