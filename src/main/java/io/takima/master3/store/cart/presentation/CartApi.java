package io.takima.master3.store.cart.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/customers/{customerId}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CartApi {

    private final CartService cartService;
    private final ArticleService articleService;
    @GetMapping()
    @JsonView(Cart.Views.FULL.class)
    public ResponseEntity<CartDTO> getAll(@PathVariable long customerId) {
        var cart = cartService.getForCustomer(customerId);
        return new ResponseEntity<>(CartDTO.fromModel(cart), HttpStatus.CREATED);
    }

    @JsonView(Cart.Views.LIGHT.class)
    @GetMapping(value = "/{cartId}", produces = "application/json")
    public  ResponseEntity<CartDTO> getCart(@PathVariable long cartId) {
        var cart = cartService.findById(cartId);

        if (!cart.getId().equals(cartId)) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(CartDTO.fromModel(cart), HttpStatus.CREATED);

    }

    @JsonView(Cart.Views.LIGHT.class)
    @PutMapping(value = "{cartId}/articles/{articleId}", produces = "application/json")
    public ResponseEntity<CartDTO> addCartArticle(@PathVariable long cartId, @PathVariable long articleId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.addArticle(article.get(),quantity);
        cartService.save(cart);
        return new ResponseEntity<>(CartDTO.fromModel(cart), HttpStatus.CREATED);
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
