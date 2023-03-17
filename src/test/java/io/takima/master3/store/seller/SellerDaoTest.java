package io.takima.master3.store.seller;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.core.utils.DatasourceSpy;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.persistence.SellerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    private PageSearch.Builder<Seller> psb;
    @BeforeEach
    void setupBuilder() {
        psb = new PageSearch.Builder<>();
    }

    @BeforeEach
    void reset() {
        this.spy.reset();
    }
    @DisplayName("method 'findById(Long id)'")
    @Nested
    class FindMethod {
    /*    @Nested
        @DisplayName("given an existing id")
        class WithValidId {
            Long sellerId = 1L;

            @Test
            @DisplayName("should return the seller")
            void shouldReturnSeller() {
                var seller = sellerDao.findById(sellerId);

                assertTrue(seller.isPresent());
                assertEquals("Joe", seller.get().getName());
            }

            // TODO uncomment when in milestone hibernate-02.
            @Test
            @DisplayName("should return the seller with its address")
            void shouldReturnSellerCart() {
                var seller = sellerDao.findById(sellerId);

                assertTrue(seller.isPresent());
                assertNotNull(seller.get().getAddress());
            }
        }
        @Nested
        @DisplayName("given an id that does not exist")
        class WithInvalidId {
            Long sellerId = 99999L;

            @Test
            @DisplayName("should return an empty observable")
            void shouldReturnSeller() {
                var seller = sellerDao.findById(sellerId);

                assertFalse(seller.isPresent());
            }
        }
    }


    @Test
    @DisplayName("should retrieve the seller from the cache the second time")
    void shouldCacheSeller() {
        sellerDao.findById(1L).get();
        em.flush();
        sellerDao.findById(1L).get();

        assertThat(spy.getQueries().length).isLessThanOrEqualTo(1);
    }

    @DisplayName("method 'findAll(PageSearch)'")
    @Nested
    class findAllMethod {

        @Nested
        @DisplayName("given a limit")
        class WithLimit {

            @Test
            @DisplayName("should no more than the limit")
            void shouldReturnCustomer() {

                var sellers = sellerDao.findAll(psb.limit(10).build());
                assertEquals(10, sellers.getContent().size());
            }
        }

        @Nested
        @DisplayName("given an offset")
        class WithOffset {

            @Test
            @DisplayName("should skip the first elements according to offset")
            void shouldSkipElements() {
                int OFFSET = 10;
                var sellers = sellerDao.findAll(psb.limit(OFFSET + 1).build());

                var sellerAtOffset = sellers.getContent().get(OFFSET);
                sellers = sellerDao.findAll(psb.offset(OFFSET).build());

                assertEquals(sellerAtOffset, sellers.getContent().get(0));
            }
        }

        @Nested
        @DisplayName("given a SearchSpecification")
        class WithSearchSpecification {

            // TODO uncomment in Step 3.1
            @Nested
            @DisplayName("with filter on id")
            class WithIdFilter {
                @Test
                @DisplayName("should filter across the given spec")
                void shouldFilter() {
                    var sellers = sellerDao.findAll(
                            psb.search(SearchSpecification.parse("id<150,id>20"))
                                    .sort(Sort.by("product.name").ascending())
                                    .build()
                    );

                    assertEquals(Long.valueOf(112), sellers.getContent().get(0).getId());
                }
            }
        }*/
    }
}
