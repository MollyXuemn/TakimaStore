package io.takima.master3.store.article.persistence;

import io.takima.master3.store.domain.Article;

import java.sql.SQLException;
import java.util.*;


public interface ArticleDao {
    public List<Article> findAll();
    public List<Article> findByName(String name);
    public Optional<Article> findById(long id);
    public List<Article> findBySellerId(long sellerId);
    public void update(Article article);
    public void create(Article article);
    public void delete(long id) throws SQLException ;
}
