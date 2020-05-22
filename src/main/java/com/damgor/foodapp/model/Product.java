package com.damgor.foodapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Product extends RepresentationModel<Product> {

    @Id
    private String id;
    private String productName;
    private Double kcal;
    private Double protein;
    private Double carbs;
    private Double fat;

    public Product(String id, String productName, Double kcal, Double protein, Double carbs, Double fat) {
        this.id = id;
        this.productName = productName;
        setKcal(kcal);
        setProtein(protein);
        setCarbs(carbs);
        setFat(fat);
    }

    public void setKcal(Double kcal) {
        if (kcal == null) kcal =0.0;
        this.kcal = round(kcal);
    }

    public void setProtein(Double protein) {
        if (protein == null) protein =0.0;
        this.protein = round(protein);
    }

    public void setCarbs(Double carbs) {
        if (carbs == null) carbs =0.0;
        this.carbs = round(carbs);
    }

    public void setFat(Double fat) {
        if (fat == null) fat =0.0;
        this.fat = round(fat);
    }

    private Double round(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
