package io.takima.master3.store;

import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.domain.Article;
import io.takima.master3.store.domain.Seller;
import io.takima.master3.store.seller.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
    private final ArticleService articleService;
    private final SellerService sellerService;

    public MainApplication(ArticleService articleService, SellerService sellerService) {
        this.articleService = articleService;
        this.sellerService = sellerService;
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
            articles.forEach(article -> LOGGER.info( String.format("\t%s %.2f %s", article.name(), article.price(), article.currency())));
        });
    }
}

