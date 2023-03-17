package io.takima.master3.store;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.core.utils.DatasourceSpy;
import io.takima.master3.store.article.persistence.ArticleDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("class ArticleDao")
class ArticleDaoTest {

    @Autowired
    DatasourceSpy spy;

    @Autowired
    ArticleDao articleDao;
    private PageSearch.Builder<Article> psb;

    @BeforeEach
    void setupBuilder() {
        psb = new PageSearch.Builder<>();
    }

    @BeforeEach
    void resetSpy() {
        this.spy.reset();
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

                var articles = articleDao.findAll(psb.limit(10).build());
                assertEquals(10, articles.getContent().size());
            }
        }

        @Nested
        @DisplayName("given an offset")
        class WithOffset {

            @Test
            @DisplayName("should skip the first elements according to offset")
            void shouldSkipElements() {
                int OFFSET = 10;
                var articles = articleDao.findAll(psb.limit(OFFSET + 1).build());

                var articleAtOffset = articles.getContent().get(OFFSET);
                articles = articleDao.findAll(psb.offset(OFFSET).build());

                assertEquals(articleAtOffset, articles.getContent().get(0));
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
                    var articles = articleDao.findAll(
                            psb.search(SearchSpecification.parse("id<150,id>20"))
                                    .sort(Sort.by("product.name").ascending())
                                    .build()
                    );

                    assertEquals(Long.valueOf(112), articles.getContent().get(0).getId());
                }
            }

            // TODO uncomment in Step 3.2
            @Nested
            @DisplayName("with filter on seller.id")
            class WithSellerIdFilter {
                @Test
                @DisplayName("should filter across the given spec")
                void shouldFilter() {
                    var articles = articleDao.findAll(
                            psb.search(SearchSpecification.parse("seller.id<150,seller.id>20"))
                                    .sort(Sort.by("product.name").ascending())
                                    .build()
                    );

                    assertEquals(Long.valueOf(165), articles.getContent().get(0).getId());
                }
            }
        }

        // TODO uncomment in Step 5.1
//        @DisplayName("should not produce N+1 fetch issue")
//        @Test
//        void shouldJoinFetchEntities() {
//            articleDao.findAll(
//                    psb.search(SearchSpecification.parse("seller.id<150,seller.id>20"))
//                            .sort(Sort.by("product.name").ascending())
//                            .build()
//            );
//
//            Pattern pattern = Pattern.compile("SELECT\\s(article|seller|product).+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
//            assertThat(Arrays.stream(spy.getQueries()).filter(q -> pattern.matcher(q).matches()).count()).isLessThanOrEqualTo(1);
//        }

        // TODO uncomment in Step 5.2
//        @Test
//        @DisplayName("should not issue a COUNT query")
//        void shouldNotIssueCountQuery() {
//            articleDao.findAll(
//                    psb.search(SearchSpecification.parse("seller.id<150,seller.id>20"))
//                            .sort(Sort.by("product.name").ascending())
//                            .build()
//            );
//            Pattern pattern = Pattern.compile("COUNT\\(.*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
//            assertFalse(Arrays.stream(spy.getQueries()).anyMatch(q -> pattern.matcher(q).find()));
//        }
    }
}
