package io.takima.master3.store.seller.models;

import io.takima.master3.store.core.models.Address;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

public record Seller (
        Long id,
        String name,
        @Column
        @Embedded
        Address address,
        String iban

) {
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
