package io.takima.master3.store.cart.models;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.customer.models.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    private Long id;

    private LocalDateTime date;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "_order")
    private List<CartArticle> cartArticles = new ArrayList<>();

    @JsonIgnore
    @JoinColumn(name = "customer_id")
    @OneToOne
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Cart() {
    }

    public Map<Article, Integer> getArticles() {
        return this.cartArticles.stream()
                .collect(Collectors.toMap(ca -> ca.getArticle(), ca -> ca.getQuantity()));
    }

    public void addArticle(Article article) {
        addArticle(article, 1);

    }

    public void addArticle(Article article, int quantity) {

        // if cartarticle for this article already exists
        Optional<CartArticle> existingCa = cartArticles.stream().filter(ca -> ca.getArticle().equals(article))
                .findAny();
        if (existingCa.isPresent()) {
            existingCa.get().setQuantity(existingCa.get().getQuantity() + quantity);
        } else {
            this.cartArticles.add(new CartArticle(this, article, quantity));
        }
    }

    public void removeArticle(Article article) {
        Optional<CartArticle> existingCa = cartArticles.stream().filter(ca -> ca.getArticle().equals(article))
                .findAny();
        if (existingCa.isPresent()) {
            cartArticles.remove(existingCa.get());
        }else {
            throw new NoSuchElementException("aaaaaaa");
        }
    }

    public void removeArticle(Article article, int quantity) {

        // if cartarticle for this article already exists
        Optional<CartArticle> existingCa = cartArticles.stream().filter(ca -> ca.getArticle().equals(article))
                .findAny();
        if (existingCa.isPresent()) {

            if (existingCa.get().getQuantity() <= quantity) {
                cartArticles.remove(existingCa.get());
            } else {
                existingCa.get().setQuantity(existingCa.get().getQuantity() - quantity);
            }
        }else {
            throw new NoSuchElementException("aaaaaaa");
        }
    }

    @PreUpdate
    @PrePersist
    private void updateDate() {
        this.date = LocalDateTime.now();
    }

    public Cart(LocalDateTime date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public Cart(Cart c) {
        this.id = c.getId();
        this.date = c.getDate();
        this.customer = c.getCustomer();
        this.cartArticles = c.getCartArticles();
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartArticle> getCartArticles() {
        return cartArticles;
    }

    public void setCartArticles(List<CartArticle> cartArticles) {
        this.cartArticles = cartArticles;
    }

    public Price getTotal() {
        // give the total amount of the articles in the cart
        List<CartArticle> cartArticles = getCartArticles();
        Currency currency = this.getCustomer().getAddress().getCountry().getCurrency();
        Price prices = new Price(0,currency);
        for (CartArticle ca : cartArticles) {
            int qty = ca.getQuantity();
            Price p = ca.getArticle().getPrice();
            prices = prices.plus(p.multiply(qty));
        }
        return prices;
    }

    public static class Builder {
        private Cart c = new Cart();

        public Cart.Builder id(Long id) {
            this.c.id = id;
            return this;
        }

        public Cart.Builder date(LocalDateTime date) {
            this.c.date = date;
            return this;
        }

        public Cart.Builder customer(Customer customer) {
            this.c.customer = customer;
            return this;
        }

        public Cart build() {
            return new Cart(c);
        }

    }

}
