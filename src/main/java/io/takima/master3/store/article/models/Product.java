package io.takima.master3.store.article.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.takima.master3.store.core.json.PriceJsonSerializer;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String ref;
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotBlank
    private String description;
    @NotBlank
    private String image;
    private String tagsCsv="";

    @lombok.Setter
    @lombok.Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",
                    column = @Column(name = "base_price"))
    })
    @JsonSerialize(using = PriceJsonSerializer.class)
    private Price basePrice;
    @JsonIgnore
    public String[] getTags() {
        // TODO split tagsCsv
        return tagsCsv != null ? tagsCsv.split(",") : new String[0];
    }

    public void setTags(String[] tags) {
        // TODO assign tagsCsv = join tags;
        tagsCsv = Arrays.stream(tags).collect(Collectors.joining(","));
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
    protected Product(Long id, String ref, String name, String brand, String description, String image, String tagsCsv, Price basePrice) {
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
    @JsonIgnore
    public String getRef() {
        return this.ref;
    }
    @JsonIgnore
    public String getName() {
        return this.name;
    }
    @JsonIgnore
    public String getBrand() {
        return this.brand;
    }
    @JsonIgnore
    public String getDescription() {
        return this.description;
    }
    @JsonIgnore
    public String getImage() {
        return this.image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", ref=" + this.getRef() + ", name=" + this.getName() + ", brand=" + this.getBrand() + ", description=" + this.getDescription() + ", image=" + this.getImage() + ", tagsCsv=" + this.getTags() + ", basePrice=" + this.getBasePrice() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
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
