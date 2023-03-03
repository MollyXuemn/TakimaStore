package io.takima.master3.store.customer.models;
import io.takima.master3.store.core.models.Address;

import javax.persistence.*;


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
    String firstName;
    @Column(name = "lastname")
    String lastName;

    @Column(unique = true)
    String email;
    @Column
    @Embedded
    private Address address;
    @Column(unique = true)
    String iban;

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

}
final class Builder {
    @Column(unique = true)
    Long id;
    @Column
    @Convert( converter = Gender.GenderConverter.class )
    Gender gender;
    @Column(name = "firstname")
    String firstName;
    @Column(name = "lastname")
    String lastName;

    @Column(unique = true)
    String email;
    @Column
    @Embedded
    private Address address;
    @Column(unique = true)
    String iban;
    public Builder() {
    }
    public Builder(Long id, Gender gender, String firstName, String lastName, String email, Address address, String iban) {
        this.id = id;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.iban = iban;
    }
    public Builder id(Long id) {
        this.id = id;
        return this;
    }
    public Builder gender(Gender gender){
        this.gender= gender;
        return this;
    }
    public Builder fName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    public Builder lName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public Builder email(String email) {
        this.email = email;
        return this;
    }
    public Builder address(Address address) {
        this.address = address;
        return this;
    }
    public Builder ib(String iban) {
        this.iban = iban;
        return this;
    }

}
