package io.takima.master3.store.article.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;

import java.util.*;

 public interface ArticleDao {
    //long count(PageSearch);
    Page<Article> findAll(PageSearch pageSearch);
    long count(PageSearch page);
    List<Article> findByProductName(String name);
    Optional<Article> findById(long id);

    Article save(Article article);
    void deleteById(long id);
}
