package io.takima.master3.store.article.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataJpaArticleDao extends ArticleDao, JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    default Page<Article> findAll(PageSearch<Article> page) {
        return findAll(page.getSearch(), page);
    }

    default long count(PageSearch page) {
        return count(page.getSearch());
    }
}

