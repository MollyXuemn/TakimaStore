package io.takima.master3.store.article.service;


import io.takima.master3.store.article.models.Article;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
     List<Article> findAll(int offset,int limit);
     List<Article> findByName(String name);
     Article findById(long articleId);
     List<Article> findBySellerId(long sellerId);
     void update(Article article);
     void create(Article article);
     void delete(long id) throws SQLException;


}