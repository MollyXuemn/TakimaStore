package io.takima.master3.store.article.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.takima.master3.store.core.json.PriceJsonSerializer;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.seller.models.Seller;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.hateoas.server.core.Relation;

@Entity
@JsonView(Article.Views.DEFAULT.class)
@Relation(value = "article", collectionRelation = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq")
    @JsonView(Views.ID.class)
    private Long id;
    @ManyToOne
    private Seller seller;
    @JsonView(Views.FULL.class)
    private int availableQuantity;
    @Embedded
    @JsonSerialize(using = PriceJsonSerializer.class)
    private Price price;
    @ManyToOne
    private Product product;
    public static class Views {
        public interface ID {}
        public interface DEFAULT extends ID, Product.Views.LIGHT {}
        public interface LIGHT extends DEFAULT, Seller.Views.LIGHT {}
        public interface FULL extends LIGHT, Seller.Views.FULL {}
    }

    public Article(Article article) {
        this.id = article.getId();
        this.seller = article.getSeller();
        this.availableQuantity = article.getAvailableQuantity();
        this.price = article.getPrice();
        this.product = article.getProduct();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Article article = (Article) o;

        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", availableQuantity=" + availableQuantity +
                ", price=" + price +
                ", product=" + product +
                '}';
    }

    public Article(Long id, Seller seller, Product product, int availableQuantity, Price price) {
        this.id = id;
        this.seller = seller;
        this.product = product;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public Article() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
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
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getRef() {
        return product.getRef();
    }
    @JsonIgnore
    public String getName() {
        return product.getName();
    }

    public String getDescription() {
        return product.getDescription();
    }

    public String getBrand() {
        return product.getBrand();
    }

    public String[] getTags() {
        return product.getTags();
    }

    public String getImage() {
        return product.getImage();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Article article) {
        return new Builder(article);
    }

    public static final class Builder {
        private Long id;
        private Seller seller;
        private Product product;
        private int availableQuantity;
        private Price price;

        public Builder() {
        }

        public Builder(Article article) {
            this.id = article.id;
            this.seller = article.seller;
            this.product = article.product;
            this.availableQuantity = article.availableQuantity;
            this.price = article.price;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder seller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
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

        public Article build() {
            return new Article(id, seller, product, availableQuantity, price);
        }

    }
}
