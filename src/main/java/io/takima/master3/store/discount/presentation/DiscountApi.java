package io.takima.master3.store.discount.presentation;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.discount.models.Offer;
import io.takima.master3.store.discount.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/customers/{customerId}/discounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscountApi {

    private final CartService cartService;
    private final DiscountService discountService;

    @Autowired
    public DiscountApi(CartService cartService, DiscountService discountService) {
        this.cartService = cartService;
        this.discountService = discountService;
    }

    @GetMapping
    public Set<Offer> list(@PathVariable long customerId) {
        Cart cart = cartService.getForCustomer(customerId);
        cart = discountService.applyOffers(cart);

        return cart.getOffers();
    }

    @PutMapping
    public void addDiscount(@PathVariable long customerId, @RequestParam String code) {
        Cart cart = cartService.getForCustomer(customerId);
        discountService.addOffer(cart, code);
    }

    @DeleteMapping
    public void removeDiscount(@PathVariable long customerId, @RequestParam String code) {
        Cart cart = cartService.getForCustomer(customerId);
        discountService.removeOffer(cart, code);
        cartService.save(cart);
    }
}
