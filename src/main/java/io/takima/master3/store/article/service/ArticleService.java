package io.takima.master3.store.article.service;


import io.takima.master3.store.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> findAll();
    List<Article> findByName(String name);
    Optional<Article> findById(long id);
    List<Article> findBySellerId(long sellerId);
    void update(Article article);
    void create(Article article);
    void delete(long id);

}