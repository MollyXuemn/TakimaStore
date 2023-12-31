package io.takima.master3.store.cart;

import io.takima.master3.store.cart.services.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("class CartService")
class CartServiceTest {

    @Autowired
    CartService cartService;

    @DisplayName("method 'getForCustomer(Long)'")
    @Nested
    class GetForCustomerMethod {

        @Nested
        @DisplayName("given an existing id")
        class WithValidId {
            Long customerId = 1L;

            @Test
            @DisplayName("should return the cart")
            void shouldReturnCustomer() {
                var cart = cartService.getForCustomer(customerId);

                assertEquals("Joe", cart.getCustomer().getFirstname());
            }
        }

        @Nested
        @DisplayName("given an id that does not exist")
        class WithInvalidId {
            Long customerId = 99999L;

            @Test
            @DisplayName("should return an empty observable")
            void shouldThrow() {
                assertThrows(NoSuchElementException.class, () -> cartService.getForCustomer(customerId));
            }
        }
    }
}

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("method save()")
@Transactional
class CartServiceSaveMethodTest {

    @Autowired
    CartService cartService;

    @Test
    @DisplayName("should persist articles")
    void shouldPersistArticles() {
        var cart = cartService.findById(1L).orElse(null);
        var article = cart.getArticles().keySet().stream().findFirst().get();
        cart.getArticles().remove(article);
        cart.removeArticle(article);
        cartService.save(cart);

        assertThat(cartService.findById(1L).orElse(null).getArticles().containsKey(article)).isFalse();

    }
}