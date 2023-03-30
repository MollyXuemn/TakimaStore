package io.takima.master3.store.article.persistence;

import io.takima.master3.store.ConnectionManager;
import io.takima.master3.store.exceptions.PersistenceException;
import io.takima.master3.store.mapper.ArticleMapper;
import io.takima.master3.store.mapper.ResultSetMapper;
import io.takima.master3.store.domain.Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface ArticleDao {
    List<Article> findAll();
    List<Article> findByName(String name);
    Optional<Article> findById(long id);
    List<Article> findBySellerId(long sellerId);


    void update(Article article);

    void create(Article article);

    void delete(long id) throws SQLException ;

}
