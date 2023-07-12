package io.takima.master3.store.product.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;


@Entity
@JsonTypeName("book")
public class Book extends Product {
    @Column
    @NotNull
    @Max(17)
    private String isbn;
    @Column
    private String author;
    @Column
    private String format;

    public Book() {
    }

    public Book(Product p, String isbn, String author, String format) {
        super(p);
        this.isbn = isbn;
        this.author = author;
        this.format = format;
    }

    public Book(Long id, String ref, String name, String brand, String description, String image, String tagsCsv, Price basePrice, String isbn, String author, String format) {
        super(id, ref, name, brand, description, image, tagsCsv, basePrice);
        this.isbn = isbn;
        this.author = author;
        this.format = format;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
