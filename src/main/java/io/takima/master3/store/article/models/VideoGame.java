package io.takima.master3.store.article.models;

import io.takima.master3.store.core.models.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("VideoGame")
@Entity
public class VideoGame extends Product  {
    @Column
    private String developer;
    @Column
    private String platform;
    @Column
    private int pegi;

    public VideoGame(Product p,VideoGame v) {
        super(p);
        this.developer = v.developer;
        this.platform = v.platform;
        this.pegi = v.pegi;
    }

    public VideoGame() {
    }
    public VideoGame(VideoGame v) {
        this(v,v);
    }

    public static class Builder {
        private Product.Builder pb = new Product.Builder();
        private VideoGame v = new VideoGame();
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
        public Builder developer(String developer) {
            this.v.developer=developer;
            return this;
        }
        public Builder platform(String platform) {
            this.v.platform=platform;
            return this;
        }
        public Builder pegi(int pegi) {
            this.v.pegi=pegi;
            return this;
        }
        public Product build() {
            return new VideoGame(pb.build(), new VideoGame(v));
        }
    }

}
