package io.takima.master3.store.article.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.takima.master3.store.core.json.PriceJsonDeserializer;
import io.takima.master3.store.core.json.PriceJsonSerializer;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.product.models.Product;
import io.takima.master3.store.seller.models.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @JsonView(Views.ID.class)
    private Long id;
    @ManyToOne
    @JsonView(Views.LIGHT.class)
    private Seller seller;
    @Column(name = "qty")
    @NotNull
    @Positive
    @JsonView(Views.FULL.class)
    private int availableQuantity;
    @Embedded
    @JsonSerialize(using = PriceJsonSerializer.class)
    @JsonDeserialize(using = PriceJsonDeserializer.class)
    @JsonView(Views.LIGHT.class)
    private Price price;
    @ManyToOne
    @JsonView(Views.LIGHT.class)
    private Product product;

    public static class Views {
        public interface ID {}
        public interface DEFAULT extends ID, Product.Views.LIGHT {}
        public interface LIGHT extends DEFAULT, Seller.Views.LIGHT {}
        public interface FULL extends LIGHT, Seller.Views.FULL {}
    }

    public Article() {
    }

    public Article(long id, Seller seller, int availableQuantity, Price price, Product product) {
        this.id = id;
        this.seller = seller;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.product = product;
    }

    public Article(Article article) {
        this.id = article.getId();
        this.seller = article.getSeller();
        this.availableQuantity = article.getAvailableQuantity();
        this.price = article.getPrice();
        this.product = article.getProduct();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Seller seller;
        private int availableQuantity;
        private Price price;
        private Product product;

        private Builder() {

        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder seller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public Builder availableQuantity(int availableQuantity) {
            this.availableQuantity = availableQuantity;
            return this;
        }

        public Builder price(Price price) {
            this.price = price;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Article build() {
            return new Article(id, seller, availableQuantity, price, product);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", seller=" + seller +
                ", availableQuantity=" + availableQuantity +
                ", price=" + price +
                ", product=" + product +
                '}';
    }
}