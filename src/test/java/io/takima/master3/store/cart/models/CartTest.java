package io.takima.master3.store.cart.models;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.core.models.Address;
import io.takima.master3.store.core.models.Country;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.customer.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DisplayName("class Cart")
class CartTest {

    @MockBean
    CartDao cartDao;

    @MockBean
    ArticleDao articleDao;

    private Cart cart;
    private Article article1;
    private Article article2;

    @BeforeEach
    void init() {
        Mockito.when(cartDao.findById(1L)).thenReturn(
                Optional.of(Cart.builder()
                        .id(1L)
                        .customer(Customer.builder()
                                .address(Address.builder()
                                        .country(Country.UNITED_STATES)
                                        .build())
                                .build())
                        .build())
        );

        Mockito.when(articleDao.findById(10L)).thenReturn(
                Optional.of(Article.builder()
                        .id(10L)
                        .build())
        );

        Mockito.when(articleDao.findById(20L)).thenReturn(
                Optional.of(Article.builder()
                        .id(20L)
                        .build())
        );

        Mockito.when(articleDao.findById(30L)).thenReturn(
                Optional.of(Article.builder()
                        .id(30L)
                        .build())
        );

        cart = cartDao.findById(1L).get();
        assertThat(cart.getArticles().isEmpty())
                .withFailMessage("Cart with id 1L was expected to be empty")
                .isTrue();
    }

    @Nested
    @DisplayName("method addArticle(Article, int)")
    class AddArticle {

        @BeforeEach
        void init() {
            article1 = articleDao.findById(10L).get();
            article2 = articleDao.findById(20L).get();
        }

        @Nested
        @DisplayName("given new articles")
        class GivenNewArticle {

            @Test
            @DisplayName("should add the articles")
            void shouldAddOneArticle() {
                cart.addArticle(article1, 1);
                cart.addArticle(article2, 2);

                assertThat(cart.getArticles().size()).isEqualTo(2);
                assertThat(cart.getArticles().get(article2)).isEqualTo(2);
                assertThat(cart.getArticles().get(new Article(article2))).isEqualTo(2);
            }
        }

        @Nested
        @DisplayName("given some articles already present in the Cart")
        class GivenExistingArticle {

            @BeforeEach
            void addArticle() {
                cart.addArticle(article1);
                cart.addArticle(article2);

                assertThat(cart.getArticles().get(article1)).isEqualTo(1);
            }

            @Test
            @DisplayName("should increment the article count")
            void shouldAddOneArticle() {
                cart.addArticle(article1, 2);

                assertThat(cart.getArticles().size()).isEqualTo(2);
                assertThat(cart.getArticles().get(article1)).isEqualTo(3);
                assertThat(cart.getArticles().get(new Article(article1))).isEqualTo(3);
            }
        }
    }

    @Nested
    @DisplayName("method removeArticle(Article)")
    class RemoveArticle {

        @Nested
        @DisplayName("given an existing article")
        class GivenExistingArticle {
            @BeforeEach
            void init() {
                article1 = articleDao.findById(10L).get();
                article2 = articleDao.findById(20L).get();

                cart.addArticle(article1, 3);
                cart.addArticle(article2, 2);
            }

            @Test
            @DisplayName("should remove the article, setting its quantity to zero")
            void shouldRemoveOneArticle() {
                cart.removeArticle(new Article(article1));

                assertThat(cart.getArticles().size()).isEqualTo(1);
                assertThat(cart.getArticles().get(article1)).isEqualTo(null);
            }
        }

        @Nested
        @DisplayName("given an article not present in the Cart")
        class GivenNonExistingArticle {

            private Article article3;

            @BeforeEach
            void init() {
                this.article3 = articleDao.findById(30L).get();
            }

            @Test
            @DisplayName("should throw an exception")
            void shouldAddOneArticle() {
                assertThrows(NoSuchElementException.class, () -> cart.removeArticle(article3));
            }
        }
    }

    @Nested
    @DisplayName("method getTotal()")
    class GetTotal {

        @Nested
        @DisplayName("on an empty cart")
        class WithEmptyCart {
            @Test
            @DisplayName("should return 0")
            void shouldReturnZero() {
                assertThat(cart.getTotal()).isEqualTo(new Price(0, Currency.USD));
            }
        }

        @Nested
        @DisplayName("on a non empty cart")
        class WithNonEmptyCart {

            @BeforeEach
            void init() {
                article1 = articleDao.findById(10L).get();
                article2 = articleDao.findById(20L).get();

                article1.setPrice(new Price(1, Currency.USD));
                article2.setPrice(new Price(5.5, Currency.USD));
                cart.addArticle(article1, 2);
                cart.addArticle(article2, 1);
            }

            @Test
            @DisplayName("should sum all articles price")
            void shouldAddAmount() {
                assertThat(cart.getTotal()).isEqualTo(new Price(7.5, Currency.USD));
            }

            @Test
            @DisplayName("should use the customer's currency")
            void shouldUseCustomerCurrency() {
                article1.setPrice(article1.getPrice().convertTo(Currency.EUR));
                article2.setPrice(article2.getPrice().convertTo(Currency.EUR));
                assertThat(cart.getTotal().getCurrency()).isEqualTo(cart.getCustomer().getAddress().getCountry().currency);
            }
        }
    }
}

