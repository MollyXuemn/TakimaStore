package io.takima.master3.store.cart.presentation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.discount.models.Offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonView(CartDTO.Views.FULL.class)

public class CartDTO {
    @JsonView(CartDTO.Views.ID.class)
    private Long id;
    // private Customer customer;
    @JsonView(CartDTO.Views.LIGHT.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @JsonView(CartDTO.Views.FULL.class)

    private Set<Offer> offers;
    /*private List<CartArticleDTO> articles;*/

    public CartDTO(Long id, LocalDateTime date, Set<Offer> offers) {
        this.id = id;
        this.date = date;
        this.offers = offers;
    }

    public static CartDTO fromModel(Cart cart) {
        var a  = new CartDTO(
                cart.getId(),
                cart.getDate(),
                cart.getOffers());

        return a;
        /*this.articles = cart.getArticles().entrySet().stream()
                .map(entry -> {
                    CartArticleDTO cartArticleDTO = CartArticleDTO.fromModel(entry.getKey());
                    cartArticleDTO.setQuantity(entry.getValue());
                    return cartArticleDTO;
                })
                .collect(Collectors.toList());*/
    }

    public static class Views {
        public interface ID {}
        public interface LIGHT extends CartDTO.Views.ID {}
        public interface FULL extends CartDTO.Views.LIGHT {}
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    public Customer getCustomer() {
//        return customer;
//    }
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }


   /* public List<CartArticleDTO> getArticles() {
        return articles;
    }*/
}
