package io.takima.master3.store.cart.presentation;

import com.fasterxml.jackson.annotation.JsonView;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/customers/{customerId}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CartApi {

    private final CartService cartService;
    private final ArticleService articleService;
    //private final CartRepresentationModelAssembler assembler;
    @GetMapping()
    @JsonView(Cart.Views.FULL.class)
    public ResponseEntity<CartDTO> getAll(@PathVariable long customerId) {
        var cart = cartService.getForCustomer(customerId);
        return new ResponseEntity<>(CartDTO.fromModel(cart), HttpStatus.CREATED);
    }

    @JsonView(Cart.Views.LIGHT.class)
    @GetMapping(value = "/{cartId}", produces = "application/json")
    public ResponseEntity<CartDTO> getCart(@PathVariable long cartId) {
        var cart = cartService.findById(cartId);

        return new ResponseEntity<>(CartDTO.fromModel(cart), HttpStatus.OK);



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
@Component
class CartRepresentationModelAssembler implements RepresentationModelAssembler<Cart, EntityModel<Cart>> {
    public EntityModel<Cart> toModel(Cart cart) {
        Link selfLink = linkTo(methodOn(CartApi.class).getCart(cart.getId())).withSelfRel();
        Link cartLink = linkTo(methodOn(CartApi.class).getCart(cart.getId())).withRel("cart");
        return EntityModel.of(cart, selfLink, cartLink);
    }
}
