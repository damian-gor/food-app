package com.damgor.foodapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class FoodDiary extends RepresentationModel<FoodDiary> {

    @Id
    @Column(updatable=false)
    private Long profileId;
    private Double caloricIntakeGoal;

    public FoodDiary(Long profileId) {
        this.profileId = profileId;
    }

    public void setCaloricIntakeGoal(Double caloricIntakeGoal) {
        this.caloricIntakeGoal = ((double)Math.round(caloricIntakeGoal/10))*10;
    }
}
