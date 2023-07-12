package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataJpaSellerDao extends SellerDao, JpaRepository<Seller, Long>, JpaSpecificationExecutor<Seller> {

    default Page<Seller> findAll(PageSearch<Seller> page) {
        return findAll(page.getSearch(), page);
    }

    default long count(PageSearch page) {
        return count(page.getSearch());
    }
}
