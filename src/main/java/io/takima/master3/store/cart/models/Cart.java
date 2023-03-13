package io.takima.master3.store.cart.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.takima.master3.store.article.models.Article;
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

    @Column
    private LocalDateTime date;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "_order")
    private List<CartArticle> cartArticles;

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
        this.removeArticle(article, 1);
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
        this.id = c.id;
        this.date = c.date;
        this.customer = c.customer;
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
