package io.takima.master3.store.cart.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.seller.models.Seller;
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
    @JsonView(Cart.Views.LIGHT.class)
    @GetMapping(value = "/{id}", produces = "application/json")
    public CartDTO getCart(@PathVariable long id) {
        return CartDTO.fromModel(cartService.findById(id));
    }
    @JsonView(Cart.Views.LIGHT.class)
    @PutMapping(value = "{cartId}/articles/{articleId}", produces = "application/json")
    public CartDTO addCartArticle(@PathVariable long cartId, @PathVariable long articleId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.addArticle(article.get(),quantity);
        cartService.save(cart);
        return CartDTO.fromModel(cart);
    }

    @DeleteMapping(value = "{cartId}/articles/{articleId}", produces = "application/json")
    public CartDTO deleteCartArticle(@PathVariable long cartId, @PathVariable long articleId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.removeArticle(article.get(), quantity);
        cartService.save(cart);
        return CartDTO.fromModel(cart);
    }
}
