package io.takima.master3.store.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.takima.master3.store.article.models.Article;
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
            fetch = FetchType.LAZY,                                          //
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
        if (this.articles == null) {
            this.articles = new LinkedHashMap<>();
            this.articles = cartArticles.stream()
                    .collect(Collectors.toMap(CartArticle::getArticle, CartArticle::getQuantity, (k1,k2)-> k1));

        }
        return this.articles;
    }

    public Cart() {
    }
    public void addArticle(Article article) {
        addArticle(article,1);
    }
    public void addArticle(Article article, int quantity) {
        getArticles().compute(article, (a, qty) -> {
            if (qty == null) {
                qty = 0;
            }
            return qty + quantity;
        });
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
        // TODO
    }


    public Cart(Long id, LocalDateTime date, List<Article> articles, Customer customer) {
        this.id = id;
        this.date = date;
        this.articles = (Map<Article, Integer>) articles;
        this.customer = customer;
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
}
