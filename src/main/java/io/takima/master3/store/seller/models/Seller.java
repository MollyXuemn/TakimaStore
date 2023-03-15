package io.takima.master3.store.seller.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Address;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Table(name = "seller")
@Entity
public class Seller {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seller_id_seq")
        Long id;
        @Column
        String name;
        @Column
        @Embedded
        Address address;
        @Column
        String iban;
        @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL,orphanRemoval = true )
        @JsonIgnore
        private List<Article> articles;

    public Seller(Long id, String name, Address address, String iban) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.iban = iban;
    }

    public Seller() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
