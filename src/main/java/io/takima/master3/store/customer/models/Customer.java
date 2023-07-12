package io.takima.master3.store.customer.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.core.models.Address;
import io.takima.master3.store.seller.models.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;


@Entity
@JsonView(Customer.Views.LIGHT.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @JsonView(Views.ID.class)
    private Long id;
    @Column
    @Convert(converter = Gender.GenderConverter.class)
    private Gender gender;
    @Column
    @NotBlank
    private String firstname;
    @Column
    @NotBlank
    private String lastname;
    @Column(unique = true)
    @Email
    private String email;
    @Embedded
    private Address address;
    @Column
    private String iban;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Cart cart;

    public static class Views {
        public interface ID {}
        public interface LIGHT extends ID, Cart.Views.ID {}
        public interface FULL extends LIGHT, Cart.Views.FULL {}
    }

    public Customer() {

    }

    public Customer(Gender gender, String firstname, String lastname, String email, Address address, String iban) {
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.iban = iban;
    }

    public Customer(Long id, Gender gender, String firstname, String lastname, String email, Address address, String iban) {
        this.id = id;
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.iban = iban;
    }

    public Long getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Gender gender;
        private String firstname;
        private String lastname;
        private String email;
        private Address address;
        private String iban;
        private Cart cart;

        private Builder(){

        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder gender(Gender gender){
            this.gender = gender;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder firsname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder iban(String iban) {
            this.iban = iban;
            return this;
        }

        public Builder cart(Cart cart){
            this.cart = cart;
            return this;
        }

        public Customer build() {
            return new Customer(id, gender, firstname, lastname, email, address, iban);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", gender=" + gender +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", iban='" + iban + '\'' +
                ", cart=" + cart +
                '}';
    }
}
