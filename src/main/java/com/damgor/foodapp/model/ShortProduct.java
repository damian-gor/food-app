package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortProduct extends RepresentationModel<ShortProduct> {
    @Id
    private Long id;
    @JsonAlias({"title"})
    private String productName;

}
