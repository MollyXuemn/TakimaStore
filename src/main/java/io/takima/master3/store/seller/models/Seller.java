package io.takima.master3.store.seller.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Objects;

@Entity
@Cacheable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_id_seq")
    @JsonView(Views.ID.class)
    private Long id;
    @Column
    @NotBlank
    @JsonView(Views.LIGHT.class)
    private String name;
    @Embedded
    @JsonView(Views.FULL.class)
    private Address address;
    @Column
    @NotBlank
    @JsonView(Views.FULL.class)
    private String iban;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Article> articles;

    public static class Views {
        public interface ID {}
        public interface LIGHT extends ID, Article.Views.DEFAULT {}
        public interface FULL extends LIGHT {}
    }


    public Seller() {

    }

    public Seller(String name, Address address, String iban) {
        this.name = name;
        this.address = address;
        this.iban = iban;
    }

    public Seller(Long id, String name, Address address, String iban) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.iban = iban;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getIban() {
        return iban;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Address address;
        private String iban;
        private List<Article> articles;

        private Builder(){

        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder iban(String iban) {
            this.iban = iban;
            return this;
        }

        public Builder articles(List<Article> articles) {
            this.articles = articles;
            return this;
        }

        public Seller build() {
            return new Seller(id, name, address, iban);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", iban='" + iban + '\'' +
                '}';
    }
}
