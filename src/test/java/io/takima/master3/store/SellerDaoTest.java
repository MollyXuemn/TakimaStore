package io.takima.master3.store;

import io.takima.master3.store.core.utils.DatasourceSpy;
import io.takima.master3.store.seller.persistence.SellerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@PersistenceContext
@Transactional
@EnableTransactionManagement
@DisplayName("SellerDao 'find'method when called twice")
class SellerDaoTest {

    @Autowired
    SellerDao sellerDao;

    @Autowired
    EntityManager em;

    @Autowired
    private DatasourceSpy spy;

    @BeforeEach
    void reset() {
        this.spy.reset();
    }

    @Test
    @DisplayName("should retrieve the seller from the cache the second time")
    void shouldCacheSeller() {
        sellerDao.findById(1L).get();
        em.flush();
        sellerDao.findById(1L).get();

        assertThat(spy.getQueries().length).isLessThanOrEqualTo(1);
    }
}
