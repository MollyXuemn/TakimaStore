package io.takima.master3.store.seller.models;

import java.util.Objects;

public record Seller (
        Long id,
        String name,
        String street,
        String city,
        String zipcode,
        String country,
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
        private String street;
        private String city;
        private String zipcode;
        private String country;
        private String iban;

        public Builder() {
        }

        public Builder(Seller seller) {
            this.id = seller.id;
            this.name = seller.name;
            this.street = seller.street;
            this.city = seller.city;
            this.zipcode = seller.zipcode;
            this.country = seller.country;
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

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder zipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder iban(String iban) {
            this.iban = iban;
            return this;
        }
        public Seller build() {
            return new Seller(id, name, street, city, zipcode, country, iban);
        }
    }
}
