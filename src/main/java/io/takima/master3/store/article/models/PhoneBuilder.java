package io.takima.master3.store.article.models;

public class PhoneBuilder {
    private Product p;
    private Phone phone;

    public PhoneBuilder setP(Product p) {
        this.p = p;
        return this;
    }

    public PhoneBuilder setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public Phone createPhone() {
        return new Phone(p, phone);
    }
}