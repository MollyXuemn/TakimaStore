package io.takima.master3.store.article.persistence.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.core.pagination.PageSearch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DataJpaArticleDao extends ArticleDao, JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    default Page<Article> findAll(PageSearch page) {
        return findAll(page.getSearch(), page);
    }
    default long count(PageSearch page) {
        return count(page.getSearch());
    }
    Article save(Article article);
    void deleteById(Long id);


}
