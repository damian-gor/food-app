package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Id;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecificProduct {
    @Id
    private Long id;
    @JsonAlias("title")
    private String productName;
    private Nutrition nutrition;

    @Data
    public static class Nutrition {
        @JsonAlias("calories")
        private Double kcal;
        private Double protein;
        private Double carbs;
        private Double fat;

        public void setProtein(String protein) {
            protein = protein.substring(0, protein.indexOf('g'));
            this.protein = Double.valueOf(protein);
        }

        public void setCarbs(String carbs) {
            carbs = carbs.substring(0, carbs.indexOf('g'));
            this.carbs = Double.valueOf(carbs);
        }

        public void setFat(String fat) {
            fat = fat.substring(0, fat.indexOf('g'));
            this.fat = Double.valueOf(fat);
        }

        //        private Nutrients nutrients;
//        @Data
//        public static class Nutrients {
//        }
    }

}
