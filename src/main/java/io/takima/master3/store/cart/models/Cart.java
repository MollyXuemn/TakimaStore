package io.takima.master3.store.cart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.models.Product;
import io.takima.master3.store.customer.models.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    private Long id;

    @Column
    private LocalDateTime date;
    @Access(AccessType.PROPERTY)
    @OneToMany(                                                                  // by this
            fetch = FetchType.EAGER,                                         //
            mappedBy = "cart",                                               //
            cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "_order")
    private List<CartArticle> cartArticles;


    @JsonIgnore
    @JoinColumn(name = "customer_id")
    @OneToOne
    private Customer customer;
    @Transient
    private Map<Article, Integer> articles;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    // will only get called by hibernate on load
    private synchronized void setCartArticles(List<CartArticle> cartArticles) {
        this.cartArticles = cartArticles;
    }

    // will only get called by hibernate on save
    private synchronized List<CartArticle> getCartArticles() {
        if (articles != null) {
                // TODO use the stream api to convert from the Map<Article, Integer> to the List<CartArticle>
            this.cartArticles.clear();
            this.cartArticles = articles.entrySet()
                                .stream()
                                .map(entry -> new CartArticle(this, entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());

            articles = null;
        }

        return cartArticles;
    }

    public synchronized Map<Article, Integer> getArticles() {
        if (this.cartArticles == null) {
            return null;
        }
        if (this.articles == null) {
            this.articles = new LinkedHashMap<>();
            this.cartArticles.stream().filter(Objects::nonNull)
                    .forEach(cartArticle -> this.articles.put(cartArticle.getArticle(), cartArticle.getQuantity()));
        }
        return this.articles;

    }

    public Cart() {
    }
    public void addArticle(Article article) {
        addArticle(article,1);
    }
    public void addArticle(Article article, int quantity) {
        getArticles().merge(article, quantity, Integer::sum);
//        compute(article, (a, qty) -> {
//            if (qty == null) {
//                qty = 0;
//            }
//            return qty + quantity;
//        });
    }

    public void removeArticle(Article article) {
        int currentQty = Optional.ofNullable(getArticles().get(article))
                .orElseThrow(() -> new NoSuchElementException(String.format("Cart does not have article %s", article.getName())));
        removeArticle(article, currentQty);
    }

    public void removeArticle(Article article, int quantity) {
        int currentQty = Optional.ofNullable(getArticles().get(article))
                .orElseThrow(() -> new NoSuchElementException(String.format("Cart does not have article %s", article.getName())));
            currentQty -= quantity;

        if (currentQty < 0) {
            currentQty = 0;
        }

        if (currentQty == 0) {
            getArticles().remove(article);
        } else {
            getArticles().put(article, currentQty);
        }
    }

    @PreUpdate
    @PrePersist
    private void updateDate() {
        this.date = LocalDateTime.now();
    }

    public Cart(Long id, LocalDateTime date, List<Article> articles, Customer customer) {
        this.id = id;
        this.date = date;
        this.articles = (Map<Article, Integer>) articles;
        this.customer = customer;
    }
    public Cart(Cart c) {
        this.id = c.id;
        this.date = c.date;
        this.articles = (Map<Article, Integer>) c.articles;
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

        public Cart.Builder articles(Map<Article, Integer> articles) {
            this.c.articles = articles;
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
