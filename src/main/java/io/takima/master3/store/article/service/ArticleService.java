package io.takima.master3.store.article.service;


import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;


public interface ArticleService {
     Page<Article> findAll(PageSearch pageSearch);
     long count(PageSearch<Article> pageSearch);
     List<Article> findByName(String name);
     Article findById(long articleId);
     void update(Article article);
     void create(Article article);
     void delete(long id) throws SQLException;
     Page<Article> findAllBySeller(Seller seller);
}