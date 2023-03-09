package io.takima.master3.store.article.models;

import io.takima.master3.store.core.models.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Phone extends Product{
    @Column
    private String resolution;
    @Column
    private String bandsCsv;
    @Column
    private Float backCamMpix;
    @Column
    private Float frontCamMpix;

    public Phone(Product p, Phone phone) {
        super(p);
        this.resolution = phone.resolution;
        this.bandsCsv = phone.bandsCsv;
        this.backCamMpix = phone.backCamMpix;
        this.frontCamMpix = phone.frontCamMpix;
    }
    public Phone(Phone phone) {
        this(phone,phone);
    }

    public Phone() {
    }

    public static class Builder {
        private Product.Builder pb = new Product.Builder();
        private Phone phone = new Phone();

        public Builder id(Long id) {
            this.pb.id(id);
            return this;
        }

        public Builder ref(String ref) {
            this.pb.ref(ref);
            return this;
        }

        public Builder name(String name) {
            this.pb.name(name);
            return this;
        }

        public Builder brand(String brand) {
            this.pb.brand(brand);
            return this;
        }

        public Builder description(String description) {
            this.pb.description(description);
            return this;
        }

        public Builder image(String image) {
            this.pb.image(image);
            return this;
        }

        public Builder tagsCsv(String tagsCsv) {
            this.pb.tagsCsv(tagsCsv);
            return this;
        }

        public Builder basePrice(Price basePrice) {
            this.pb.basePrice(basePrice);
            return this;
        }
        public Builder resolution(String resolution) {
            this.phone.resolution=resolution;
            return this;
        }
        public Builder bandsCsv(String bandsCsv) {
            this.phone.bandsCsv = bandsCsv;
            return this;
        }

        public Builder backCamMpix(Float backCamMpix) {
            this.phone.backCamMpix = backCamMpix;
            return this;
        }

        public Builder frontCamMpix(Float frontCamMpix) {
            this.phone.frontCamMpix = frontCamMpix;
            return this;
        }

        public Product build() {
            return new Phone(pb.build(), phone);
        }
    }
}
