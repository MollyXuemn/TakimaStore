package io.takima.master3.store.cart.presentation;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.discount.models.Offer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartDTO {
        private Long id;
        private Customer customer;
        private LocalDateTime date;
        private Set<Offer> offers;
        private List<CartArticleDTO> articles;


    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.customer = cart.getCustomer();
        this.date = cart.getDate();
        this.offers = cart.getOffers();
        this.articles = cart.getArticles().entrySet().stream()
                .map(entry -> {
                    CartArticleDTO cartArticleDTO = CartArticleDTO.fromModel(entry.getKey());
                    cartArticleDTO.setQuantity(entry.getValue());
                    return cartArticleDTO;
                })
                .collect(Collectors.toList());
    }
    public static CartDTO fromModel(Cart cart) {
        return new CartDTO(cart);
    }


    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public List<CartArticleDTO> getArticles() {
        return articles;
    }
}
