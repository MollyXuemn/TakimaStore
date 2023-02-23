package io.takima.master3.store.domain;

import java.util.Objects;

public record Article(
        Long id,
        Seller seller,
        String ref,
        String name,
        String description,
        String image,
        int availableQuantity,
        double price,
        String currency
) {

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Article article) {
        return new Builder(article);
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

    public static final class Builder {
        private Long id;
        private Seller seller;
        private String ref;
        private String name;
        private String description;
        private String image;
        private int availableQuantity;
        private double price;
        private String currency;

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
            this.currency= article.currency;
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

        public Builder price(double price) {
            this.price = price;
            return this;
        }
        public Builder currency(String  currency) {
            this.currency = currency;
            return this;
        }

        public Article build() {
            return new Article(id, seller, ref, name, description, image, availableQuantity, price, currency);
        }

    }
}
