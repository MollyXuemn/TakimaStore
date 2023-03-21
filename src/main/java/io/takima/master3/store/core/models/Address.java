package io.takima.master3.store.core.models;


import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Address {
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipcode;
    @Convert(converter = Country.CountryConverter.class)
    public Country country;

    public Address() {
    }

    public Address(String street, String city, String zipcode, Country country) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    public static Address builder() {
        return new Address();
    }
    public Address build() {
        return this;
    }

    public Address country(Country country) {
        this.country = country;
        return this;
    }
    public static final class Builder {
        private String street;
        private String city;
        private String zipcode;
        private Country country;

        public Builder() {
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder zipCode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder country(Country country) {
            this.country = country;
            return this;
        }

        public Address build() {
            return new Address(street, city, zipcode, country);
        }
    }


}

