package io.takima.master3.store.article.models;

import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.seller.models.Seller;

import javax.persistence.*;
import java.util.Objects;
@Table(name = "article")
@Entity
public class Article {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="article_id_seq")
        public Long id;
        @ManyToOne
        public Seller seller;
        @Column
        public String ref;
        @Column
        public
        String name;
        @Column
        public String description;
        @Column
        public String image;
        @Column
        public int availableQuantity;
        @Column
        @Embedded
        public Price price;

    public Article(Long id, Seller seller, String ref, String name, String description, String image, int availableQuantity, Price price) {
        this.id = id;
        this.seller = seller;
        this.ref = ref;
        this.name = name;
        this.description = description;
        this.image = image;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public Article() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Article article) {
        return new Builder(article);
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public static final class Builder {
        private Long id;
        private Seller seller;
        private String ref;
        private String name;
        private String description;
        private String image;
        private int availableQuantity;
        private Price price;

        public Builder() {
        }

        public Builder(Article article) {
            this.id = article.id;
            this.seller = article.seller;
            this.ref = article.ref;
            this.name = article.name;
            this.description = article.description;
            this.image = article.image;
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

        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
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
            return new Article(id, seller, ref, name, description, image, availableQuantity, price);
        }

    }
}
