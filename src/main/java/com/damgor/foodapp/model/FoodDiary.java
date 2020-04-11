package com.damgor.foodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FoodDiary extends RepresentationModel<FoodDiary> {

    @Id
    @Column(updatable=false)
    private Long profileId;
    private Integer caloricIntakeGoal;

//    @OneToMany
//    private List<DiaryPage> diaryPages;

}
