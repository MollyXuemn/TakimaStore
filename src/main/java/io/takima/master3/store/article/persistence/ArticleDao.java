package io.takima.master3.store.article.persistence;

import io.takima.master3.store.article.models.Article;

import java.sql.SQLException;
import java.util.*;

 public interface ArticleDao {
    List<Article> findAll(int offset,int limit);
    List<Article> findByName(String name);
    Optional<Article> findById(long id);

    List<Article> findBySellerId(long sellerId);

    Article update(Article article);
    Article create(Article article);

    void delete(long id) throws SQLException;
}
