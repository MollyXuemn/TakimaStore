package io.takima.master3.store.article.persistence.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataJpaArticleDao extends ArticleDao, JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    Page<Article> findAllBySeller(Pageable page, Seller seller);

    default Page<Article> findAll(PageSearch page) {
        return null;
        // TODO uncomment later in step 3
        // return findAll(page.getSearch(), page);
    }

    default long count(PageSearch page) {
        return 0;
        // TODO uncomment later in step 3 // return count(page.getSearch());
    }
}
