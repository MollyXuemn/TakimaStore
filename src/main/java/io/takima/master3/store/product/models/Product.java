package io.takima.master3.store.product.models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.takima.master3.store.core.json.PriceJsonDeserializer;
import io.takima.master3.store.core.json.PriceJsonSerializer;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = Product.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = VideoGame.class, name = "videogame"),
        @JsonSubTypes.Type(value = Phone.class, name = "phone")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @JsonView(Views.ID.class)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String ref;
    @Column
    @NotBlank
    private String name;
    @Column
    private String brand;
    @Column
    @NotBlank
    private String description;
    @Column
    @NotBlank
    private String image;
    @Column
    private String tagsCsv;

    @Embedded
    @JsonSerialize(using = PriceJsonSerializer.class)
    @JsonDeserialize(using = PriceJsonDeserializer.class)
    @AttributeOverrides({
            @AttributeOverride(name = "amount",
                    column = @Column(name = "base_price"))
    })
    private Price basePrice;

    public static class Views {
        public interface ID {
        }

        public interface LIGHT extends ID {
        }

        public interface FULL extends LIGHT {
        }
    }

    @JsonView(Views.FULL.class)
    public String[] getTags() {
        return tagsCsv != null ? tagsCsv.split(",") : new String[0];
    }

    public void setTags(String[] tags) {
        tagsCsv = String.join(",", tags);
    }

    public Product(Product p) {
        this.id = p.id;
        this.ref = p.ref;
        this.name = p.name;
        this.brand = p.brand;
        this.description = p.description;
        this.image = p.image;
        this.basePrice = p.basePrice;
        this.tagsCsv = p.tagsCsv;
    }

    @java.beans.ConstructorProperties({"id", "ref", "name", "brand", "description", "image", "tagsCsv", "basePrice"})
    protected Product(Long id, @NotBlank String ref, @NotBlank String name, @NotBlank String brand, @NotBlank String description, @NotBlank String image, String tagsCsv, Price basePrice) {
        this.id = id;
        this.ref = ref;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.tagsCsv = tagsCsv;
        this.basePrice = basePrice;
    }

    protected Product() {
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank String getRef() {
        return this.ref;
    }

    public @NotBlank String getName() {
        return this.name;
    }

    public @NotBlank String getBrand() {
        return this.brand;
    }

    public @NotBlank String getDescription() {
        return this.description;
    }

    public @NotBlank String getImage() {
        return this.image;
    }

    public String getTagsCsv() {
        return this.tagsCsv;
    }

    public Price getBasePrice() {
        return this.basePrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRef(@NotBlank String ref) {
        this.ref = ref;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setBrand(@NotBlank String brand) {
        this.brand = brand;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public void setImage(@NotBlank String image) {
        this.image = image;
    }

    public void setTagsCsv(String tagsCsv) {
        this.tagsCsv = tagsCsv;
    }

    public void setBasePrice(Price basePrice) {
        this.basePrice = basePrice;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", ref=" + this.getRef() + ", name=" + this.getName() + ", brand=" + this.getBrand() + ", description=" + this.getDescription() + ", image=" + this.getImage() + ", tagsCsv=" + this.getTagsCsv() + ", basePrice=" + this.getBasePrice() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof final Product other)) return false;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        return Objects.equals(this$id, other$id);
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Product;
    }


    public static class Builder {

        private Product p = new Product();

        public Builder id(Long id) {
            this.p.id = id;
            return this;
        }

        public Builder ref(String ref) {
            this.p.ref = ref;
            return this;
        }

        public Builder name(String name) {
            this.p.name = name;
            return this;
        }

        public Builder brand(String brand) {
            this.p.brand = brand;
            return this;
        }

        public Builder description(String description) {
            this.p.description = description;
            return this;
        }

        public Builder image(String image) {
            this.p.image = image;
            return this;
        }

        public Builder tagsCsv(String tagsCsv) {
            this.p.tagsCsv = tagsCsv;
            return this;
        }

        public Builder basePrice(Price basePrice) {
            this.p.basePrice = basePrice;
            return this;
        }

        public Product build() {
            return new Product(p);
        }
    }
}
