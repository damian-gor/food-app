package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealId implements Serializable {

    private Long profileId;
    private Date date;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mealNumber;

    public MealId(Long profileId, Date date) {
        this.profileId = profileId;
        this.date = date;
    }
}
