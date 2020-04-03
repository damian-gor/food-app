package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@Data
public class Profile extends RepresentationModel<Profile> {

    @Id
    @Column(updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique=true)
    private String name;
    private Cuisine cuisine;
    private Diet diet;
    private Intolerance intolerance;
    private ArrayList<Integer> favouriteRecipes;
}
