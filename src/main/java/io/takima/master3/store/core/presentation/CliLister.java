package io.takima.master3.store.core.presentation;

import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CliLister {

    final static Logger LOGGER = LoggerFactory.getLogger(CliLister.class);

    private final ArticleService articleService;
    private final SellerService sellerService;

    public CliLister(
            ArticleService articleService,
            SellerService sellerService
    ) {
        this.articleService = articleService;
        this.sellerService = sellerService;
    }

/*    public Map<Seller, List<Article>> list() {

        // get all sellers
        LOGGER.info("gathering seller data...");
        var sellers = sellerService.findAll();

        LOGGER.info("got {} sellers", sellers.size());
        var map = new HashMap<Seller, List<Article>>();

        LOGGER.info("gathering articles data...");
        // get all products for all sellers
        sellers.forEach(seller -> map.computeIfAbsent(seller, s -> articleService.findAllBySeller(s).getContent()));
        LOGGER.info("got {} articles", map.values().stream().mapToInt(List::size).sum());

        return map;
    }*/
}