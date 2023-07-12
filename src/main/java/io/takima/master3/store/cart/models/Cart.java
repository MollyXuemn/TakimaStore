package io.takima.master3.store.cart.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.discount.models.Offer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonView(Cart.Views.LIGHT.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    @JsonView(Views.ID.class)
    private Long id;

    @JoinColumn(name = "customer_id")
    @OneToOne
    @JsonIgnore
    private Customer customer;

    @Column
    private LocalDateTime date;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "cart",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "_order")
    @JsonIgnore
    //@JsonView(Views.FULL.class)
    private List<CartArticle> cartArticles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "cart_offer",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    @JsonView(Views.FULL.class)
    private Set<Offer> offers = new HashSet<>();

    public static class Views {
        public interface ID {
        }

        public interface LIGHT extends ID, Article.Views.LIGHT, Offer.Views.LIGHT {
        }

        public interface FULL extends LIGHT {
        }
    }

    public Cart() {
    }

    public Cart(Cart cart) {
        this.id = cart.getId();
        this.customer = cart.getCustomer();
        this.date = cart.getDate();
        this.cartArticles = cart.getCartArticles();
        this.offers = cart.getOffers();
    }

    public Cart(long id, Customer customer, LocalDateTime date, List<CartArticle> cartArticles, Set<Offer> offers) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.cartArticles = cartArticles;
        this.offers = offers;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<CartArticle> getCartArticles() {
        return cartArticles;
    }

    public void addArticle(Article article) {
        this.addArticle(article, 1);
    }

    public void removeArticle(Article article) {
        Optional<CartArticle> existingCa = cartArticles.stream()
                .filter(ca -> ca.getArticle().equals(article))
                .findAny();
        if (existingCa.isPresent()) {
            cartArticles.remove(existingCa.get());
        } else {
            throw new NoSuchElementException("No element found in the cart");
        }
    }

    public Price getTotal() {
        return this.getCartArticles()
                .stream()
                .map(cartArticle -> cartArticle.getArticle()
                        .getPrice()
                        .multiply(cartArticle.getQuantity()))
                .reduce(new Price(0, this.getCustomer()
                        .getAddress()
                        .getCountry().currency), Price::plus);
    }

    public void addArticle(Article article, int quantity) {
        Optional<CartArticle> existingCa = cartArticles.stream()
                .filter(ca -> ca.getArticle().equals(article))
                .findAny();
        if (existingCa.isPresent()) {
            existingCa.get().setQuantity(existingCa.get().getQuantity() + quantity);
        } else {
            this.cartArticles.add(new CartArticle(this, article, quantity));
        }
    }


    public void removeArticle(Article article, int quantity) {
        Optional<CartArticle> existingCa = cartArticles.stream()
                .filter(ca -> ca.getArticle().equals(article))
                .findAny();
        if (existingCa.isPresent()) {
            if (existingCa.get().getQuantity() <= quantity) {
                cartArticles.remove(existingCa.get());
            } else {
                existingCa.get().setQuantity(existingCa.get().getQuantity() - quantity);
            }
        } else {
            throw new NoSuchElementException("No element found in the cart");
        }
    }


    @PreUpdate
    @PrePersist
    private void updateDate() {
        this.date = LocalDateTime.now();
    }

    private void setCartArticles(List<CartArticle> cartArticles) {
        this.cartArticles = cartArticles;
    }

    public Map<Article, Integer> getArticles() {
        return this.cartArticles.stream()
                .collect(Collectors.toMap(ca -> ca.getArticle(), ca -> ca.getQuantity()));
    }


    public Set<Offer> getOffers() {
        return offers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Customer customer;
        private LocalDateTime date;
        private List<CartArticle> cartArticles = new ArrayList<>();
        private Set<Offer> offers = new HashSet<>();
        private Map<Article, Integer> articleIntegerMap;

        private Builder() {

        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder cartArticles(List<CartArticle> cartArticles) {
            this.articleIntegerMap = new HashMap<>();
            this.cartArticles = cartArticles;
            return this;
        }

        public Builder offers(Set<Offer> offers) {
            this.offers = offers;
            return this;
        }

        public Builder articles(Map<Article, Integer> articleMap) {
            this.cartArticles = new ArrayList<>();
            this.articleIntegerMap = articleMap;
            return this;
        }

        public Cart build() {
            Cart c = new Cart(id, customer, date, cartArticles, offers);
            if (articleIntegerMap != null) {
                articleIntegerMap.forEach(c::addArticle);
            }
            return c;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", date=" + date +
                ", cartArticles=" + cartArticles +
                '}';
    }
}