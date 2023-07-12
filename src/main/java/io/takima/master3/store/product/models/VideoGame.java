package io.takima.master3.store.product.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;


@Entity
@JsonTypeName("videogame")
public class VideoGame extends Product {
    @Column
    @NotNull
    private String developer;
    @Column
    @NotNull
    private String platform;
    @Column
    private Integer pegi;

    public VideoGame() {
    }

    public VideoGame(Product p, String developer, String platform, int pegi) {
        super(p);
        this.developer = developer;
        this.platform = platform;
        this.pegi = pegi;
    }

    public VideoGame(Long id, String ref, String name, String brand, String description, String image, String tagsCsv, Price basePrice, String developer, String platform, int pegi) {
        super(id, ref, name, brand, description, image, tagsCsv, basePrice);
        this.developer = developer;
        this.platform = platform;
        this.pegi = pegi;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getPegi() {
        return pegi;
    }

    public void setPegi(int pegi) {
        this.pegi = pegi;
    }

    @Override
    public String toString() {
        return "VideoGame{" +
                "developer='" + developer + '\'' +
                ", platform='" + platform + '\'' +
                ", pegi=" + pegi +
                '}';
    }
}
