package io.takima.master3.store.cart.presentation;

import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartApi {

    private final CartService cartService;
    private final ArticleService articleService;

    @Autowired
    CartApi(CartService cartService, ArticleService articleService) {
        this.cartService = cartService;
        this.articleService = articleService;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/getCart", produces = "application/json")
    public Cart getCart(@RequestParam() long id) {
        return cartService.findById(id);
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/addCartArticle", produces = "application/json")
    public Cart addCartArticle(@RequestParam long cartId, long articleId, int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.addArticle(article.get(),quantity);
        //cartService.save(cart);
        return cart;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/deleteCartArticle", produces = "application/json")
    public Cart deleteCartArticle(@RequestParam() long cartId, long articleId, int quantity) {
        var article = articleService.findById(articleId);
        var cart = cartService.findById(cartId);
        cart.removeArticle(article.get(), quantity);
        cartService.save(cart);
        return cart;
    }
}
