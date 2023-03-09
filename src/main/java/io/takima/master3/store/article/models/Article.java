package io.takima.master3.store.article.models;

import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.seller.models.Seller;
import jakarta.persistence.*;

@Table(name = "article")
@Entity
public class Article {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="article_id_seq")
        private Long id;
        @ManyToOne
        private Seller seller;
        @Column
        private int availableQuantity;

        @Embedded
        private Price price;
        @ManyToOne
        private Product product;


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
