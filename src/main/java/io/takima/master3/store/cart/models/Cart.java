package io.takima.master3.store.cart.models;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.discount.models.Offer;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonView(Cart.Views.FULL.class)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    @JsonView(Views.ID.class)
    private Long id;

    @JsonView(Views.LIGHT.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "_order")
    @JsonIgnore
    private List<CartArticle> cartArticles = new ArrayList<>();

    @JoinColumn(name = "customer_id")
    @OneToOne
    private Customer customer;
    @ManyToMany
    @JoinTable(name = "cart_offer",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    @JsonView(Views.FULL.class)
    private Set<Offer> offers = new HashSet<>();
    public static class Views {
        public interface ID {}
        public interface LIGHT extends ID, Article.Views.LIGHT, Offer.Views.LIGHT {}
        public interface FULL extends LIGHT {}
    }

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
        this.offers = c.getOffers();
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
            throw new NoSuchElementException("No such article");
        }
    }

    @PreUpdate
    @PrePersist
    private void updateDate() {
        this.date = LocalDateTime.now();
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

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Price getTotal() {
        // give the total amount of the articles in the cart
        Currency currency = customer.getAddress().getCountry().getCurrency();

        return new Price(getArticles().entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(e.getValue()))
                .map(p -> p.convertTo(currency))
                .mapToDouble(Price::getAmount)
                .sum(), currency);

    }

    public static class Builder {
        private Cart c = new Cart();
        private Map<Article, Integer> articles;


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
        public Cart.Builder offers(Set<Offer> offers) {
            this.c.offers = offers;
            return this;
        }
        public Cart.Builder cartArticles(List<CartArticle> cartArticles) {
            this.articles = new HashMap<>();
            this.c.cartArticles = cartArticles;
            return this;
        }
        public Cart.Builder articles(Map<Article, Integer> articles) {
            this.c.cartArticles = new ArrayList<>();
            this.articles = articles;
            return this;
        }

        public Cart build() {
            Cart cart= new Cart(c);
            if (articles != null){
                articles.forEach(cart::addArticle);
            }
                return cart;
        }

    }

}
