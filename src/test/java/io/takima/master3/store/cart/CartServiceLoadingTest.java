package io.takima.master3.store.cart;

import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.core.utils.DatasourceSpy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("CartService")
@Transactional
class CartServiceLoadingTest {
    @Autowired
    CartService cartService;

    @Autowired
    DatasourceSpy spy;

    @BeforeEach
    void resetSafeGuard() {

        this.spy.reset();
    }


    @Test
    @DisplayName("should issue no more than 1 query method 'findById(Long id)' when adding an article to the cart")
    void shouldIssueLessThan1Queries() {
        var cart = cartService.findById(1L).orElse(null);
        var article = cart.getArticles().keySet().stream().findFirst().get();
        cart.addArticle(article);
        assertThat(spy.getQueries().length).isLessThanOrEqualTo(1);
    }
}
