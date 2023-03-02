package io.takima.master3.store.customer.models;

import javax.persistence.*;


@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="customer_id_seq")
    @Column(unique = true)
    Long id;
    @Column
    String gender;
    @Column(name = "firstname")
    String firstName;
    @Column(name = "lastname")
    String lastName;

    @Column(unique = true)
    String email;
    @Column
    String street;
    @Column
    String city;
    @Column
    String zipcode;
    @Column
    String country;
    @Column(unique = true)
    String iban;
    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
