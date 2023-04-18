package io.takima.master3.store.cart.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.presentation.CustomerApi;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/customers/{customerId}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CartApi {

    private final CartService cartService;
    private final ArticleService articleService;
    //private final CartRepresentationModelAssembler assembler;
    @GetMapping()
    @JsonView(CartDTO.Views.FULL.class)
    public CartDTO getCart(@PathVariable long customerId) {

        Cart cart = cartService.getForCustomer(customerId);
        return CartDTO.fromModel(cart);
    }


    @JsonView(Cart.Views.FULL.class)
    @PutMapping("{cartId}/articles/{articleId}")
    public Cart addCartArticle(@PathVariable long customerId, @PathVariable long articleId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        Article article= articleService.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", articleId)));

        Cart cart = cartService.getForCustomer(customerId);
        cart.addArticle(article,quantity);
        cartService.save(cart);
        return cart;
    }

    @DeleteMapping(value = "{cartId}/articles/{articleId}", produces = "application/json")
    public Cart deleteCartArticle(@PathVariable long customerId, @PathVariable long articleId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        Article article= articleService.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", articleId)));

        Cart cart = cartService.getForCustomer(customerId);
        cart.removeArticle(article, quantity);
        cartService.save(cart);
        return cart;
    }
}
