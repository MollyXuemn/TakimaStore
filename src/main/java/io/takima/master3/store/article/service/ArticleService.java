package io.takima.master3.store.article.service;


import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageResponse;
import io.takima.master3.store.core.pagination.PageSearch;

import java.sql.SQLException;
import java.util.List;


public interface ArticleService {
     PageResponse<Article> findAll(PageSearch pageSearch);
     long count(PageSearch<Article> pageSearch);
     List<Article> findByName(String name);
     Article findById(long articleId);
     PageResponse<Article> findBySellerId(long sellerId);
     void update(Article article);
     void create(Article article);
     void delete(long id) throws SQLException;


}