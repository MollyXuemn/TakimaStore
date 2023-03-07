package io.takima.master3.store.article.models;

import io.takima.master3.store.core.models.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Book extends Product{
    @Column
    private String isbn;
    @Column
    private String author;
    @Column
    private String format;

    public Book(Product p, Book b) {
        super(p);
        this.isbn = b.isbn;
        this.author = b.author;
        this.format = b.format;
    }
    public Book(Book b) {
        this(b,b);
    }

    public Book() {

    }

    public static class Builder {
        private Product.Builder pb = new Product.Builder();
        private Book b = new Book();
        public Builder id(Long id) {
            this.pb.id(id);
            return this;
        }
        public Builder ref(String ref) {
            this.pb.ref(ref);
            return this;
        }
        public Builder name(String name) {
            this.pb.name(name);
            return this;
        }
        public Builder brand(String brand) {
            this.pb.brand(brand);
            return this;
        }
        public Builder description(String description) {
            this.pb.description(description);
            return this;
        }
        public Builder image(String image) {
            this.pb.image(image);
            return this;
        }
        public Builder tagsCsv(String tagsCsv) {
            this.pb.tagsCsv(tagsCsv);
            return this;
        }
        public Builder basePrice(Price basePrice) {
            this.pb.basePrice(basePrice);
            return this;
        }
        public Builder isbn(String isbn) {
            this.b.isbn=isbn;
            return this;
        }

        public Builder author(String author) {
            this.b.author=author;
            return this;
        }
        public Builder format(String format) {
            this.b.format=format;
            return this;
        }
        public Product build() {
            return new Book(pb.build(), new Book(b));
        }
    }


}
