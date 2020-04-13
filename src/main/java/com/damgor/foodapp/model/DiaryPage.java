package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPage extends RepresentationModel<DiaryPage> {

    @EmbeddedId
    private DiaryPageId id;
    private Integer caloricIntakeGoal;
    private Integer actualCaloricIntake;
    private Integer caloricBalance;
    private Double actualProteinIntake;
    private Double actualCarbsIntake;
    private Double actualFatIntake;

    public DiaryPage(Long profileId, Date date, Integer caloricIntakeGoal, Integer actualCaloricIntake, Integer caloricBalance,
                     Double actualProteinIntake, Double actualCarbsIntake, Double actualFatIntake) {
        if (date==null) date = new java.sql.Date(System.currentTimeMillis());
        this.id = new DiaryPageId(profileId,date);
        this.caloricIntakeGoal = caloricIntakeGoal;
        this.actualCaloricIntake = actualCaloricIntake;
        this.caloricBalance = caloricBalance;
        this.actualProteinIntake = actualProteinIntake;
        this.actualCarbsIntake = actualCarbsIntake;
        this.actualFatIntake = actualFatIntake;
    }

    public DiaryPage(Long profileId, Integer caloricIntakeGoal, Integer actualCaloricIntake, Integer caloricBalance,
                     Double actualProteinIntake, Double actualCarbsIntake, Double actualFatIntake) {
        this.id = new DiaryPageId(profileId,new java.sql.Date(System.currentTimeMillis()));
        this.caloricIntakeGoal = caloricIntakeGoal;
        this.actualCaloricIntake = actualCaloricIntake;
        this.caloricBalance = caloricBalance;
        this.actualProteinIntake = actualProteinIntake;
        this.actualCarbsIntake = actualCarbsIntake;
        this.actualFatIntake = actualFatIntake;
    }
}


