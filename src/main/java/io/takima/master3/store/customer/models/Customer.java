package io.takima.master3.store.customer.models;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.core.models.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="customer_id_seq")
    @Column(unique = true)
    Long id;
    @Column
    @Convert( converter = Gender.GenderConverter.class )
    Gender gender;
    @Column(name = "firstname")
    @NotBlank
    String firstName;
    @Column(name = "lastname")
    @NotBlank
    String lastName;
    @Column(unique = true)
    String email;
    @Column
    @Embedded
    @NotBlank
    Address address;
    @Column
    @NotBlank
    String iban;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Customer(Long id, Gender gender, String firstName, String lastName, String email, Address address, String iban) {
        this.id = id;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.iban = iban;
    }

    public Customer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", gender=" + gender +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", iban='" + iban + '\'' +
                ", cart=" + cart +
                '}';
    }

    public static Customer.Builder builder() {

        return new Customer.Builder();
    }
    public static final class Builder {
         Long id;

        //Mandatory fields
         Gender gender;
         String firstName;
         String lastName;
         String email;
         String iban;
         Address address;


        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
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
        public Builder address(Address address) {
            this.address = address;
            return this;
        }


        public Customer build(){

            return new Customer(id, gender, firstName,lastName,email,address,iban);
        }


    }
}



