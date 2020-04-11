package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends RepresentationModel<Product> implements Diarable{

    @Id
    private String id;
    private String productName;
    private Double kcal;
    private Double protein;
    private Double carbs;
    private Double fat;
}
