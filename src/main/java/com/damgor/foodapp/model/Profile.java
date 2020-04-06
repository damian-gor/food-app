package com.damgor.foodapp.model;

import com.damgor.foodapp.model.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;



//potem przetestowac @Data zamiast tak wielu
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private ArrayList<Integer> favouriteRecipes;
    private ArrayList<String> favouriteProducts;

}
