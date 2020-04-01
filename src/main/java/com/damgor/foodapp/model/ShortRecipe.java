package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShortRecipe {
    @Id
    private int id;
    @JsonAlias({"title"})
    private String recipeTitle;
    private int likes;
    private int usedIngredientCount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UsedIngredients> usedIngredients;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> usedIngredientsList;
    private int readyInMinutes;


    public List<UsedIngredients> getUsedIngredients() {
        return null;
    }

    public void setUsedIngredients(List<UsedIngredients> usedIngredients) {
        this.usedIngredients = usedIngredients;
        this.usedIngredientsList = new ArrayList<>();
        for (UsedIngredients ingr : this.usedIngredients) {
            this.usedIngredientsList.add(ingr.getName());
        }
    }
}
