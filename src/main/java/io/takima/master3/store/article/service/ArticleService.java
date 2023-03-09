package io.takima.master3.store.article.service;


import io.takima.master3.store.article.models.Article;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    public List<Article> findAll();
    public List<Article> findByName(String name);
    public Optional<Article> findById(long id);
    public List<Article> findBySellerId(long sellerId);
    public void update(Article article);
    public void create(Article article);
    public void delete(long id) throws SQLException;
    public Article findById(long articleId);

}