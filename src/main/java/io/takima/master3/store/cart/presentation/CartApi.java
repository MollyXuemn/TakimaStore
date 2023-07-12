package io.takima.master3.store.cart.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/customers/{customerId}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartApi {

    private final CartService cartService;
    private final ArticleService articleService;

    @Autowired
    CartApi(CartService cartService, ArticleService articleService) {
        this.cartService = cartService;
        this.articleService = articleService;
    }

    @GetMapping
    @JsonView(Cart.Views.FULL.class)
    public CartDTO getCart(@PathVariable long customerId) {
        return new CartDTO().fromModel(cartService.getForCustomer(customerId));
    }

    @PutMapping
    public Cart addCartArticle(@PathVariable long customerId, @RequestParam long articleId, int quantity) {
        Cart cartToFind = cartService.getForCustomer(customerId);
        Article articleToFind = articleService.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", articleId)));

        cartToFind.addArticle(articleToFind, quantity);

        cartService.save(cartToFind);

        return cartToFind;
    }

    @DeleteMapping
    public Cart deleteCartArticle(@PathVariable long customerId, @RequestParam() long articleId, int quantity) {
        Cart cartToFind = cartService.getForCustomer(customerId);
        Article articleToFind = articleService.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", articleId)));

        cartToFind.removeArticle(articleToFind, quantity);

        cartService.save(cartToFind);

        return cartToFind;
    }
}
