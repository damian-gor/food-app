package com.damgor.foodapp.model;

import com.damgor.foodapp.model.enums.Cuisine;
import com.damgor.foodapp.model.enums.Diet;
import com.damgor.foodapp.model.enums.Intolerance;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Profile extends RepresentationModel<Profile> {

    @Id
    @Column(updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique=true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;
    @Enumerated(EnumType.STRING)
    private Diet diet;
    @Enumerated(EnumType.STRING)
    private Intolerance intolerance;
    @Column(columnDefinition = "MEDIUMBLOB")
    private ArrayList<Integer> favouriteRecipes;
    @Column(columnDefinition = "MEDIUMBLOB")
    private ArrayList<String> favouriteProducts;

}
