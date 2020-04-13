package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Id;
import java.net.URL;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe extends RepresentationModel<Recipe> {

    @Id
    private Integer id;
    @JsonAlias({"title"})
    private String recipeTitle;
    @JsonAlias({"aggregateLikes"})
    private int likes;
    @JsonAlias({"spoonacularScore"})
    private double score;
    private double healthScore;
    private int readyInMinutes;
    private int servings;
    private double pricePerServing;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> diets;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> dishTypes;
    @JsonAlias({"spoonacularSourceUrl"})
    private URL recipeURL;
    @JsonAlias({"extendedIngredients"})
    private List<Ingredients> ingredients;
    private String instructions;

    //    Changing from cents to dollars
    public void setPricePerServing(double pricePerServing) {
        this.pricePerServing = ((double) ((int) pricePerServing)) / 100;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions
                .replaceAll("[\t\n\r]", " ")
                .replaceAll("\\<[^>]*>"," ")
                .replaceAll("  ","")
                .trim();
    }
}

