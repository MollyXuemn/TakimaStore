package io.takima.master3.store.cart.presentation;

import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/customers/{customerId}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CartApi {

    private final CartService cartService;
    private final ArticleService articleService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public CartDTO getCart(@PathVariable long id) {
        return CartDTO.fromModel(cartService.findById(id));
    }

    @PutMapping(value = "{cartId}/articles", produces = "application/json")
    public Cart addCartArticle(@PathVariable long cartId, @PathVariable long articleId, @PathVariable int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.addArticle(article.get(),quantity);
        //cartService.save(cart);
        return cart;
    }

    @DeleteMapping(value = "{cartId}/articles", produces = "application/json")
    public Cart deleteCartArticle(@PathVariable long cartId, @PathVariable long articleId, @PathVariable int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.removeArticle(article.get(), quantity);
        cartService.save(cart);
        return cart;
    }
}
