package io.takima.master3.store.product.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.takima.master3.store.core.models.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;

@Entity
@JsonTypeName("phone")
public class Phone extends Product {
    @Column
    private String resolution;
    @Column
    private String bands_csv;
    @Column
    @Positive
    private Float back_cam_mpix;
    @Column
    @Positive
    private Float front_cam_mpix;

    public Phone() {
    }

    public Phone(Product p, String resolution, String bands_csv, float back_cam_mpix, float front_cam_mpix) {
        super(p);
        this.resolution = resolution;
        this.bands_csv = bands_csv;
        this.back_cam_mpix = back_cam_mpix;
        this.front_cam_mpix = front_cam_mpix;
    }

    public Phone(Long id, String ref, String name, String brand, String description, String image, String tagsCsv, Price basePrice, String resolution, String bands_csv, float back_cam_mpix, float front_cam_mpix) {
        super(id, ref, name, brand, description, image, tagsCsv, basePrice);
        this.resolution = resolution;
        this.bands_csv = bands_csv;
        this.back_cam_mpix = back_cam_mpix;
        this.front_cam_mpix = front_cam_mpix;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getBands_csv() {
        return bands_csv;
    }

    public void setBands_csv(String bands_csv) {
        this.bands_csv = bands_csv;
    }

    public Float getBack_cam_mpix() {
        return back_cam_mpix;
    }

    public void setBack_cam_mpix(float back_cam_mpix) {
        this.back_cam_mpix = back_cam_mpix;
    }

    public Float getFront_cam_mpix() {
        return front_cam_mpix;
    }

    public void setFront_cam_mpix(float front_cam_mpix) {
        this.front_cam_mpix = front_cam_mpix;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "resolution='" + resolution + '\'' +
                ", bands_csv='" + bands_csv + '\'' +
                ", back_cam_mpix=" + back_cam_mpix +
                ", front_cam_mpix=" + front_cam_mpix +
                '}';
    }
}
