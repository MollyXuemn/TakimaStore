package io.takima.master3.store.core.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Embeddable
public class Address {
    @Column
    private String street;
    @Column private String city;
    @Column private String zipcode;
    @Column
    public String country;

    public String toString () {

        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }
}

