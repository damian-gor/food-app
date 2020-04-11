package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealId implements Serializable {


    private Long profileId;
    @Temporal(TemporalType.DATE)
    private Date date;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mealNumber;

}
