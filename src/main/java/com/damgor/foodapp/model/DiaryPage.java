package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPage extends RepresentationModel<DiaryPage> {

    @EmbeddedId
    private DiaryPageId id;
    private Double caloricIntakeGoal = 0.0;
    private Double actualKcalIntake = 0.0;
    private Double kcalLeft = 0.0;
    private Double actualProteinIntake = 0.0;
    private Double actualCarbsIntake = 0.0;
    private Double actualFatIntake = 0.0;


    public DiaryPage(Long profileId, Date date) {
        if (date == null) date = new java.sql.Date(System.currentTimeMillis());
        this.id = new DiaryPageId(profileId, date);
    }

    public DiaryPage(Long profileId) {
        new DiaryPage(profileId, null);
    }

    public void setNutrients(Double actualKcalIntake, Double actualProteinIntake, Double actualCarbsIntake, Double actualFatIntake) {
        this.actualKcalIntake = round(actualKcalIntake);
        this.actualProteinIntake = round(actualProteinIntake);
        this.actualCarbsIntake = round(actualCarbsIntake);
        this.actualFatIntake = round(actualFatIntake);
        setKcalLeft(round(caloricIntakeGoal - actualKcalIntake));
    }

    public void setCaloricIntakeGoal(Double caloricIntakeGoal) {
        this.caloricIntakeGoal = caloricIntakeGoal;
        setKcalLeft(round(caloricIntakeGoal - actualKcalIntake));
    }

    private Double round(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}


