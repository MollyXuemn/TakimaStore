package io.takima.master3.store.discount;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.article.persistence.impl.DataJpaArticleDao;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.cart.persistence.impl.JpaCartDao;
import io.takima.master3.store.core.models.Address;
import io.takima.master3.store.core.models.Country;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.discount.models.AmountOffer;
import io.takima.master3.store.discount.models.PercentOffer;
import io.takima.master3.store.discount.persistence.JpaOfferDao;
import io.takima.master3.store.discount.persistence.OfferDao;
import io.takima.master3.store.discount.services.DiscountServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("DiscountService")
class DiscountServiceTest {

    @Autowired
    DiscountServiceImpl discountService;

    @MockBean
    JpaCartDao cartDao;

    @MockBean
    DataJpaArticleDao articleDao;

    @MockBean
    JpaOfferDao offerDao;

    @Autowired
    Clock clock;

    private Article.Builder articleBuilder;
    private Cart.Builder cartBuilder;
    private PercentOffer.PercentOfferBuilder percentOfferBuilder;
    private AmountOffer.AmountOfferBuilder amountOfferBuilder;

    @BeforeEach
    void init() {
        // for easy mocking, you can preset your builders here with some conveniant values.
        // preset article builder (ie: default id, default price, ...)
        articleBuilder = Article.builder();

        // preset cart builder (ie: default offers, default customer & country & currency....)
        cartBuilder = new Cart.Builder()
                .offers(new HashSet<>())
                .customer(Customer.builder()
                        .address(Address.builder()
                                .country(Country.USA)
                                .build())
                        .build())
                .articles(Map.of(articleBuilder.id(1L).price(new Price(100, Currency.DOLLAR)).build(), 1));

        // preset percent offer builder here (ie: default validity dates, default percent...)
        percentOfferBuilder = PercentOffer.builder()
                .startDate(oneYearAgo())
                .expireDate(oneYearAhead());

        // preset amount offer builder here (ie: default validity dates, default amount...)
        amountOfferBuilder = AmountOffer.builder()
                .startDate(oneYearAgo())
                .expireDate(oneYearAhead());

        // you may want to have some default mocks.
        Mockito.when(cartDao.findById(1L))
                .thenReturn(Optional.of(cartBuilder.build()));
    }

    @AfterEach
    void resetSpies() {
        // TODO 1 what is the purpose of those lines ?
        Mockito.reset(cartDao, articleDao, offerDao);
        Mockito.clearInvocations(cartDao);
    }

    // method applyOffers() with no code
    @Nested
    @DisplayName("method 'applyOffers(Cart)'")
    class ApplyOfferWithoutCode {

        @Nested
        @DisplayName("given an empty cart")
        class WithEmptyCart {

            @BeforeEach
            void init() {
                Mockito.when(cartDao.findById(1L))
                        .thenReturn(Optional.of(cartBuilder.articles(new HashMap<>()).build()));

            }

            @Test
            @DisplayName("should give total = 0")
            void shouldTotal0() {
                Cart cart = cartDao.findById(1L).get();
                Price expectedPrice = cart.getTotal();
                assertThat(expectedPrice).isEqualTo(expectedPrice.minus(expectedPrice));

                discountService.applyOffers(cart);

                assertThat(cart.getTotal()).isEqualTo(expectedPrice);
            }
        }

        @Nested
        @DisplayName("given cart with some articles")
        class WithNonEmptyCart {

            @Nested
            @DisplayName("when no ongoing offer available")
            class WithNoOngoingOffer {
                @BeforeEach
                void init() {
                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(new HashSet<>());

                }

                @Test
                @DisplayName("should give total = 0")
                void shouldDoNothing() {
                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    assertThat(expectedPrice.toString()).isNotEqualTo(expectedPrice.minus(expectedPrice).toString());

                    discountService.applyOffers(cart);

                    assertThat(cart.getTotal().toString()).isEqualTo(expectedPrice.toString());
                }
            }

            @Nested
            @DisplayName("when some ongoing offers available")
            class WithOngoingPercentOffer {

                @Test
                @DisplayName("should apply 'percent offers' in increasing order")
                void shouldApplyAllPercentOffers() {

                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    percentOfferBuilder.id(1L).percent(30).build(),
                                    percentOfferBuilder.id(2L).percent(10).build(),
                                    percentOfferBuilder.id(3L).percent(20).build()
                            ));

                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount *= ((100.0 - 10.0) / 100.0);
                    expectedAmount *= ((100.0 - 20.0) / 100.0);
                    expectedAmount *= ((100.0 - 30.0) / 100.0);

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                }

                @Test
                @DisplayName("should apply 'amount offers'")
                void shouldApplyAllAmountOffers() {

                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    amountOfferBuilder.id(1L).amount(new Price(30, Currency.DOLLAR)).build(),
                                    amountOfferBuilder.id(2L).amount(new Price(10, Currency.DOLLAR)).build(),
                                    amountOfferBuilder.id(3L).amount(new Price(20, Currency.DOLLAR)).build()
                            ));

                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount -= 10.0;
                    expectedAmount -= 20.0;
                    expectedAmount -= 30.0;

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                }

                @Test
                @DisplayName("should apply 'amount offers' before 'percent offers'")
                void shouldApplyAmountBeforePercent() {
                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    amountOfferBuilder.id(1L).amount(new Price(30, Currency.DOLLAR)).build(),
                                    percentOfferBuilder.id(2L).percent(10L).build()
                            ));

                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount -= 30.0;
                    expectedAmount *= ((100.0 - 10.0) / 100.0);

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                }
            }

            @Nested
            @DisplayName("and offer have a quantity criteria")
            class WithQuantityCriteria {

                @BeforeEach
                void init() {
                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    percentOfferBuilder.id(1L)
                                            .minQuantity(2)
                                            .maxQuantity(4)
                                            .percent(10.0).build()
                            ));
                }

                @Nested
                @DisplayName("if cart meets the quantity criteria")
                class IfQuantityCriteriaMatches {
                    @BeforeEach
                    void init() {
                        Mockito.when(cartDao.findById(1L))
                                .thenReturn(
                                        Optional.of(cartBuilder
                                                .articles(Map.of(articleBuilder.id(1L)
                                                        .price(new Price(300, Currency.DOLLAR))
                                                        .build(), 2))
                                                .build()));
                    }

                    @Test
                    @DisplayName("should apply offers")
                    void shouldApplyOffer() {
                        Cart cart = cartDao.findById(1L).get();
                        Price expectedPrice = cart.getTotal();
                        double expectedAmount = expectedPrice.getAmount();
                        expectedAmount *= ((100.0 - 10.0) / 100.0);

                        cart = discountService.addOffer(cart, "test");

                        assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                    }
                }

                @Nested
                @DisplayName("if quantity criteria does not match")
                class IfQuantityCriteriaNotMatches {

                    @BeforeEach
                    void init() {
                        Mockito.when(cartDao.findById(1L))
                                .thenReturn(
                                        Optional.of(cartBuilder
                                                .articles(Map.of(articleBuilder.id(1L)
                                                        .price(new Price(300, Currency.DOLLAR))
                                                        .build(), 6))
                                                .build()));
                    }

                    @Test
                    @DisplayName("should not apply the offers")
                    void shouldNotApplyOffer() {
                        Cart cart = cartDao.findById(1L).get();
                        Price expectedPrice = cart.getTotal();

                        cart = discountService.addOffer(cart, "test");

                        assertThat(cart.getTotal()).isEqualTo(expectedPrice);
                    }
                }
            }
        }

        @Nested
        @DisplayName("and offer have a 'price criteria'")
        class WithQuantityCriteria {

            @BeforeEach
            void init() {
                Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                        .thenReturn(Set.of(
                                percentOfferBuilder.id(1L)
                                        .minPrice(new Price(590, Currency.DOLLAR))
                                        .maxPrice(new Price(800, Currency.DOLLAR))
                                        .percent(10.0).build()
                        ));
            }

            @Nested
            @DisplayName("if cart meets the price criteria")
            class IfQuantityCriteriaMatches {
                @BeforeEach
                void init() {
                    Mockito.when(cartDao.findById(1L))
                            .thenReturn(
                                    Optional.of(cartBuilder
                                            .articles(Map.of(articleBuilder.id(1L)
                                                    .price(new Price(300, Currency.DOLLAR))
                                                    .build(), 2))
                                            .build()));
                }

                @Test
                @DisplayName("should apply offers")
                void shouldApplyOffer() {
                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount *= ((100.0 - 10.0) / 100.0);

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                }
            }

            @Nested
            @DisplayName("if price criteria does not match")
            class IfQuantityCriteriaNotMatches {

                @BeforeEach
                void init() {
                    Mockito.when(cartDao.findById(1L))
                            .thenReturn(
                                    Optional.of(cartBuilder
                                            .articles(Map.of(articleBuilder.id(1L)
                                                    .price(new Price(300, Currency.DOLLAR))
                                                    .build(), 6))
                                            .build()));
                }

                @Test
                @DisplayName("should not apply the offers")
                void shouldNotApplyOffer() {
                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(expectedPrice);
                }
            }
        }


        @Nested
        @DisplayName("and offer have a 'article selection criteria'")
        class WithArticleSelectionCriteria {
            List<Article> articlesSelection;
            Map<Article, Integer> articles;
            @BeforeEach
            void init() {
                articlesSelection = List.of(
                        articleBuilder.id(1L).price(new Price(100, Currency.DOLLAR)).build(),
                        articleBuilder.id(2L).price(new Price(200, Currency.DOLLAR)).build(),
                        articleBuilder.id(3L).price(new Price(300, Currency.DOLLAR)).build());

                articles = Map.of(articlesSelection.get(0), 1, articlesSelection.get(1), 2);

                Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock), articles.keySet()))
                        .thenReturn(Set.of(
                                percentOfferBuilder.id(1L)
                                        .articles(new HashSet<>(articlesSelection))
                                        .percent(10.0).build()
                        ));
            }

            @Nested
            @DisplayName("if cart meets the article selection criteria")
            class IfArticleSelectionCriteriaMatches {
                @BeforeEach
                void init() {
                    Mockito.when(cartDao.findById(1L))
                            .thenReturn(
                                    Optional.of(cartBuilder
                                            .id(1L)
                                            .articles(new HashMap<>(articles))
                                            .build()));
                }

                @Test
                @DisplayName("should apply offers")
                void shouldApplyOffer() {
                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount *= ((100.0 - 10.0) / 100.0);

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                }
            }

            @Nested
            @DisplayName("if article selection criteria does not match")
            class IfQuantityCriteriaNotMatches {

                @BeforeEach
                void init() {
                    Mockito.when(cartDao.findById(1L))
                            .thenReturn(
                                    Optional.of(cartBuilder
                                            .articles(new HashMap<>(Map.of(articleBuilder.id(4L)
                                                    .price(new Price(300, Currency.DOLLAR))
                                                    .build(), 6)))
                                            .build()));
                }

                @Test
                @DisplayName("should not apply the offers")
                void shouldNotApplyOffer() {
                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal()).isEqualTo(expectedPrice);
                }
            }

            @DisplayName("in addition to 'quantity criteria'")
            @Nested
            class WithQuantityCriteria {

                @Nested
                @DisplayName("if cart does not have the required quantity of articles in the selection")
                class IfQuantityCriteriaNotMatch {
                    @BeforeEach
                    void init() {
                        Mockito.when(cartDao.findById(1L))
                                .thenReturn(
                                        Optional.of(cartBuilder
                                                .build()));

                        Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock), articles.keySet()))
                                .thenReturn(Set.of(
                                        percentOfferBuilder.id(1L)
                                                .articles(Set.of(articlesSelection.get(0)))
                                                .minQuantity(2)
                                                .percent(10.0).build()
                                ));
                    }

                    @Test
                    @DisplayName("should not apply the offer")
                    void shouldNotApplyOffer() {
                        Cart cart = cartDao.findById(1L).get();
                        Price expectedPrice = cart.getTotal();
                        double expectedAmount = expectedPrice.getAmount();

                        cart = discountService.addOffer(cart, "test");

                        assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                    }
                }
            }
        }

        @Test
        @DisplayName("should persist the cart with its applied offers")
        void shouldPersistTheCart() {
            Cart cart = cartDao.findById(1L).get();
            discountService.applyOffers(cart);
            Mockito.verify(cartDao).update(Mockito.any(Cart.class)); //
        }
    }
    @Nested
    @DisplayName("method 'applyOffers(Cart, String)'")
    class ApplyOfferWithCode {
        @BeforeEach
        void init() {
            throw new UnsupportedOperationException();
        }
    }

    @Nested
    @DisplayName("method 'addOffers(Cart,String)'")
    class AddOfferWithoutCode {
        @Nested
        @DisplayName("given an empty cart")
        class WithEmptyCart {
            @BeforeEach
            void init() {
                Mockito.when(offerDao.findByCode("test"))
                        .thenReturn(Optional.ofNullable(percentOfferBuilder
                                .code("test")
                                .percent(10)
                                .build()));
            }
            @Test
            @DisplayName("should throw a DiscountException")
            void shouldTotal0() {
                Mockito.when(cartDao.findById(1L))
                        .thenReturn(Optional.of(cartBuilder.articles(new HashMap<>()).build()));

                Cart cart = cartDao.findById(1L).get();
                assertThrows(DiscountException.class, () -> discountService.addOffer(cart, "test"));
            }
            @Test
            @DisplayName("should throw a NoSuchElementException")
            void shouldThrow() {
                Mockito.when(cartDao.findById(1L))
                        .thenReturn(Optional.empty());

                Cart cart = cartDao.findById(1L).get();
                assertThrows(NoSuchElementException.class, () -> discountService.addOffer(cart, "test"));
            }
        }
        @Nested
        @DisplayName("given cart with some articles")
        class WithNonEmptyCart {
            @Nested
            @DisplayName("when no ongoing offer available")
            class WithNoOngoingOffer {
                @BeforeEach
                void init() {
                    Mockito.when(cartDao.findById(1L))
                            .thenReturn(Optional.of(cartBuilder.articles(new HashMap<>()).build()));
                    Mockito.when(offerDao.findByCode("test"))
                            .thenReturn(Optional.ofNullable(percentOfferBuilder
                                            .id(1L)
                                    .percent(10)
                                    .code("test")
                                    .expireDate(LocalDateTime.now())
                                    .build()));
                }
                @Test
                @DisplayName("should throw either a DiscountException or NoSuchElementException")
                void shouldDoNothing() {
                    Cart cart = cartDao.findById(1L).get();
                    assertThrows(DiscountException.class, () -> discountService.addOffer(cart, "test"));
                }
            }

            @Nested
            @DisplayName("when some ongoing offers available for this code")
            class WithOngoingPercentOffer {
                @Test
                @DisplayName("should apply 'percent offers' in increasing order")
                void shouldApplyAllPercentOffers() {
                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    percentOfferBuilder.id(1L).percent(30).build(),
                                    percentOfferBuilder.id(2L).percent(10).build(),
                                    percentOfferBuilder.id(3L).percent(20).build()
                            ));
                    Mockito.when(offerDao.findByCode("test"))
                            .thenReturn(Optional.of(percentOfferBuilder
                                    .code("test").id(4L).percent(15).build()));


                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount *= ((100.0 - 10.0) / 100.0);
                    expectedAmount *= ((100.0 - 15.0) / 100.0);
                    expectedAmount *= ((100.0 - 20.0) / 100.0);
                    expectedAmount *= ((100.0 - 30.0) / 100.0);

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal().toString()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()).toString());
                }
                @Test
                @DisplayName("should apply 'amount offers'")
                void shouldApplyAllAmountOffers() {

                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    amountOfferBuilder.id(1L).amount(new Price(30, Currency.DOLLAR)).build(),
                                    amountOfferBuilder.id(2L).amount(new Price(10, Currency.DOLLAR)).build(),
                                    amountOfferBuilder.id(3L).amount(new Price(20, Currency.DOLLAR)).build()
                            ));

                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount -= 10.0;
                    expectedAmount -= 20.0;
                    expectedAmount -= 30.0;

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal().toString()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()).toString());
                }

                @Test
                @DisplayName("should apply 'amount offers' before 'percent offers'")
                void shouldApplyAmountBeforePercent() {
                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    amountOfferBuilder.id(1L).amount(new Price(30, Currency.DOLLAR)).build(),
                                    percentOfferBuilder.id(2L).percent(10L).build()
                            ));

                    Cart cart = cartDao.findById(1L).get();
                    Price expectedPrice = cart.getTotal();
                    double expectedAmount = expectedPrice.getAmount();
                    expectedAmount -= 30.0;
                    expectedAmount *= ((100.0 - 10.0) / 100.0);

                    cart = discountService.addOffer(cart, "test");

                    assertThat(cart.getTotal().toString()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()).toString());
                }
            }

            @Nested
            @DisplayName("and offer have a quantity criteria")
            class WithQuantityCriteria {

                @BeforeEach
                void init() {
                    Mockito.when(offerDao.findOffersWithoutCode(LocalDateTime.now(clock)))
                            .thenReturn(Set.of(
                                    percentOfferBuilder.id(1L)
                                            .minQuantity(2)
                                            .maxQuantity(4)
                                            .percent(10.0).build()
                            ));
                }

                @Nested
                @DisplayName("if cart meets the quantity criteria")
                class IfQuantityCriteriaMatches {
                    @BeforeEach
                    void init() {
                        Mockito.when(cartDao.findById(1L))
                                .thenReturn(
                                        Optional.of(cartBuilder
                                                .articles(Map.of(articleBuilder.id(1L)
                                                        .price(new Price(300, Currency.DOLLAR))
                                                        .build(), 2))
                                                .build()));
                    }

                    @Test
                    @DisplayName("should apply offers")
                    void shouldApplyOffer() {
                        Cart cart = cartDao.findById(1L).get();
                        Price expectedPrice = cart.getTotal();
                        double expectedAmount = expectedPrice.getAmount();
                        expectedAmount *= ((100.0 - 10.0) / 100.0);

                        cart = discountService.addOffer(cart, "test");

                        assertThat(cart.getTotal()).isEqualTo(new Price(expectedAmount, cart.getTotal().getCurrency()));
                    }
                }

                @Nested
                @DisplayName("if quantity criteria does not match")
                class IfQuantityCriteriaNotMatches {

                    @BeforeEach
                    void init() {
                        Mockito.when(cartDao.findById(1L))
                                .thenReturn(
                                        Optional.of(cartBuilder
                                                .articles(Map.of(articleBuilder.id(1L)
                                                        .price(new Price(300, Currency.DOLLAR))
                                                        .build(), 6))
                                                .build()));
                    }

                    @Test
                    @DisplayName("should not apply the offers")
                    void shouldNotApplyOffer() {
                        Cart cart = cartDao.findById(1L).get();
                        Price expectedPrice = cart.getTotal();

                        cart = discountService.addOffer(cart, "test");

                        assertThat(cart.getTotal()).isEqualTo(expectedPrice);
                    }
                }
            }
        }



    }

    LocalDateTime oneYearAgo() {
        return LocalDateTime.now(clock).minus(1, ChronoUnit.YEARS);
    }

    LocalDateTime oneYearAhead() {
        return LocalDateTime.now(clock).plus(1, ChronoUnit.YEARS);
    }
}


@Configuration
class MockTimeConfig {
    @Bean
    @Primary
    Clock fixedClock() { // LocalDateTime.now(clock) will always return 2050-01-01
        return Clock.fixed(Instant.parse("2050-01-01T12:00:00.00Z"), ZoneId.of("UTC"));
    }
}
