package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredients {
    private String name;
    @JsonAlias({"originalString"})
    private String description;
    private Double amount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String unit;

    public void setAmount(Double amount) {
        this.amount = round(amount);
    }

    private Double round(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
