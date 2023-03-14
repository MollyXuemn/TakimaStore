package io.takima.master3.store.article.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageResponse;
import io.takima.master3.store.core.pagination.PageSearch;
import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.*;

 public interface ArticleDao {
    //long count(PageSearch);
    PageResponse<Article> findAll(PageSearch pageSearch);

    List<Article> findByName(String name);
    Optional<Article> findById(long id);

    List<Article> findBySellerId(long sellerId);

    Article update(Article article);
    Article create(Article article);

    void delete(long id) throws SQLException;
}
