package io.takima.master3.store.article.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    /**
     * Find an article by its ID
     *
     * @param id the id of article to findById
     * @return the article with the searched id.
     */
    Optional<Article> findById(long id);

    /**
     * Get a page of Article, searched by the given page-search.
     * @param pageSearch the page-search. Searches for a given specification in a given order with a given limit and a given offset.
     * @return the page of all articles that matches the given search
     */
    Page<Article> findAll(PageSearch<Article> pageSearch);

    /**
     * Count the number of Articles that corresponds to the search.
     * @param pageSearch the page-search. Searches for a given specification in a given order with a given limit and a given offset.
     * @return the number of articles that matches the given search
     */
    long count(PageSearch pageSearch);

    /**
     * Save an article
     * @param article the article to save
     */
    Article save(Article article);

    /**
     * Delete an article by id
     * @param id the id of article to delete
     */
    void deleteById(long id);
}
