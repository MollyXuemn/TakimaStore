package io.takima.master3.store.article.service;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findById(long id);

    Page<Article> findAll(PageSearch<Article> pageSearch);

    List<Article> findByName(String name);

    Page<Article> findBySeller(Seller seller);

    void update(Article article);

    void create(Article article);

    void delete(long id);
}