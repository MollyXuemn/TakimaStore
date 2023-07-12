package io.takima.master3.store.cart.presentation;

import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.models.CartArticle;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.discount.models.Offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

class CartDTO {
    private Long id;
    private LocalDateTime date;
    private Set<Offer> offers;
    private List<CartArticle> articles;

    public CartDTO() {
    }

    public CartDTO fromModel(Cart cart){
        this.id = cart.getId();
        this.date = cart.getDate();
        this.offers = cart.getOffers();
        this.articles = cart.getCartArticles();

        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<CartArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<CartArticle> articles) {
        this.articles = articles;
    }
}


