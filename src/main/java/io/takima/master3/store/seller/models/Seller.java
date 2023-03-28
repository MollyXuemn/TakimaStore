package io.takima.master3.store.seller.models;

import com.fasterxml.jackson.annotation.*;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Cacheable
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seller {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seller_id_seq")
        @JsonView(Views.ID.class)
        Long id;
        @NotBlank
        @JsonView(Views.LIGHT.class)
        String name;
        @Embedded
        @JsonView(Views.FULL.class)
        Address address;
        @JsonView(Views.FULL.class)
        String iban;
        @JsonView(Views.FULL.class)
        @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL,orphanRemoval = true)
        @JsonIgnore
        private List<Article> articles;
        public static class Views {
            public interface ID {}
            public interface LIGHT extends ID {}
            public interface PAGE extends LIGHT {}
            public interface FULL extends PAGE {}
        }


    public Seller(Long id, String name, Address address, String iban) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.iban = iban;
    }

    public Seller() {
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
    @JsonIgnore
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Seller seller) {
        return new Builder(seller);
    }

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

    public static final class Builder {
        private Long id;
        private String name;
        private Address address;
        private String iban;

        public Builder() {
        }

        public Builder(Seller seller) {
            this.id = seller.id;
            this.name = seller.name;
            this.address = seller.address;
            this.iban = seller.iban;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        @Enumerated(STRING)
        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder iban(String iban) {
            this.iban = iban;
            return this;
        }
        public Seller build() {
            return new Seller(id, name, address, iban);
        }
    }
}
