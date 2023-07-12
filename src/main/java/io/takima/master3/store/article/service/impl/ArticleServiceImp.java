package io.takima.master3.store.article.service.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.pagination.PageResponse;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.core.utils.Monitored;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp implements ArticleService {
    private final ArticleDao articleDao;

    @Autowired
    public ArticleServiceImp(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public Optional<Article> findById(long id) {
        return articleDao.findById(id);
    }

    @Override
    public Page<Article> findAll(PageSearch<Article> pageSearch) {
        Page<Article> tempArticles = articleDao.findAll(pageSearch);
        return new PageResponse<>(pageSearch, tempArticles.getTotalElements(), tempArticles.stream().map(this::remapCurrency).toList());
    }

    @Override
    public List<Article> findByName(String name) {
        List<Article> tempArticles = articleDao.findAll(PageSearch.<Article>builder()
                .search(SearchSpecification.parse("product.name=" + name))
                .build()).getContent();
        return remapCurrency(tempArticles);
    }

    @Monitored
    @Override
    public Page<Article> findBySeller(Seller seller) {
        Page<Article> tempArticles = articleDao.findAll(PageSearch
                .<Article>builder()
                .search(SearchSpecification.parse("seller.id=" + seller.getId()))
                .build());
        return new PageImpl<>(tempArticles.stream().map(this::remapCurrency).toList());
    }

    @Override
    public void create(Article article) {
        articleDao.save(article);
    }

    @Override
    public void update(Article article) {
        articleDao.save(article);
    }

    @Override
    public void delete(long id) {
        articleDao.deleteById(id);
    }

    private List<Article> remapCurrency(List<Article> articles) {
        return articles.stream()
                .map(this::remapCurrency)
                .collect(Collectors.toList());
    }

    private Article remapCurrency(Article article) {
        Currency sellerCurrency = article.getSeller().getAddress().getCountry().currency;

        return Article.builder()
                .id(article.getId())
                .seller(article.getSeller())
                .availableQuantity(article.getAvailableQuantity())
                .price(article.getPrice().convertTo(sellerCurrency))
                .product(article.getProduct())
                .build();
    }
}
