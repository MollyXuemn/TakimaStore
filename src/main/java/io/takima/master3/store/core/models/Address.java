package io.takima.master3.store.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String zipcode;
    @Column
    @Convert(converter = Country.CountryConverter.class)
    private Country country;

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

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Country getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private String street;
        private String city;
        private String zipcode;
        private Country country;

        private Builder(){

        }

        public Builder street(String street){
            this.street = street;
            return this;
        }

        public Builder city(String city){
            this.city = city;
            return this;
        }

        public Builder zipcode(String zipcode){
            this.zipcode = zipcode;
            return this;
        }

        public Builder country(Country country){
            this.country = country;
            return this;
        }

        public Address build(){
            return  new Address(street, city, zipcode, country);
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", country=" + country +
                '}';
    }
}

