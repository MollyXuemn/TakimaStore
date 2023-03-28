package io.takima.master3.store.discount.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.services.CartService;
import io.takima.master3.store.discount.models.Offer;
import io.takima.master3.store.discount.services.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/customers/{customerId}/carts/{cartId}/discounts")
@AllArgsConstructor
public class DiscountApi {

    private final CartService cartService;
    private final DiscountService discountService;
    @JsonView(Offer.Views.LIGHT.class)
    @GetMapping(value = "",  produces = "application/json")
    public Set<Offer> list(@PathVariable long customerId) {
        Cart cart = cartService.getForCustomer(customerId);
        cart = discountService.applyOffers(cart);

        return cart.getOffers();
    }
    @PutMapping(value = "",  produces = "application/json")
    public void addDiscount(@PathVariable long customerId, @RequestParam String code) {
        Cart cart = cartService.getForCustomer(customerId);
        discountService.addOffer(cart, code);
    }

    @DeleteMapping (value = "",  produces = "application/json")
    public void removeDiscount(@PathVariable long customerId, @RequestParam String code) {
        Cart cart = cartService.getForCustomer(customerId);
        discountService.removeOffer(cart, code);
        cartService.save(cart);
    }
}
