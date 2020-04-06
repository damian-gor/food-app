package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularOutput {
    private List<Recipe> recipes;
    private List<ShortRecipe> results;
    private List<ShortProduct> products;
}
