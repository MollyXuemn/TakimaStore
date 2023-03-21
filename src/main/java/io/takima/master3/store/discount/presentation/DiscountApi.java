package io.takima.master3.store.discount.presentation;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.discount.models.Offer;
import io.takima.master3.store.discount.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("/api")
public class DiscountApi {

    private final CartService cartService;
    private final DiscountService discountService;

    @Autowired
    public DiscountApi(CartService cartService, DiscountService discountService) {
        this.cartService = cartService;
        this.discountService = discountService;
    }

    // TODO this endpoint is not RESTful. If you already followed the course on REST, refactor this endpoint to be RESTFul
    @ResponseBody
    @GetMapping(value = "/getDiscounts",  produces = "application/json")
    public Set<Offer> list(@RequestParam long customerId) {
        Cart cart = cartService.getForCustomer(customerId);
        cart = discountService.applyOffers(cart);

        return cart.getOffers();
    }

    // TODO this endpoint is not RESTful. If you already followed the course on REST, refactor this endpoint to be RESTFul
    @ResponseBody
    @GetMapping(value = "/addDiscount",  produces = "application/json")
    public void addDiscount(@RequestParam long customerId, @RequestParam String code) {
        Cart cart = cartService.getForCustomer(customerId);
        discountService.addOffer(cart, code);
    }

    // TODO this endpoint is not RESTful. If you already followed the course on REST, refactor this endpoint to be RESTFul
    @ResponseBody
    @GetMapping(value = "/removeDiscount",  produces = "application/json")
    public void removeDiscount(@RequestParam long customerId, @RequestParam String code) {
        Cart cart = cartService.getForCustomer(customerId);
        discountService.removeOffer(cart, code);
        cartService.save(cart);
    }
}
