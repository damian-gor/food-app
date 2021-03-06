package com.damgor.foodapp.model;

import com.damgor.foodapp.model.enums.ActivityLevel;
import com.damgor.foodapp.model.enums.Aim;
import com.damgor.foodapp.model.enums.Sex;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Entity
public class ProfileDetails extends RepresentationModel<ProfileDetails> {

    @Id
    @Column(updatable = false)
    private Long profileId;
    @Min(80)
    @Max(250)
    private Double height; // cm
    @Min(20)
    @Max(250)
    private Double weight; // kg
    @Min(1920)
    private Integer yearOfBirth;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Aim aim;
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;


    @Setter(AccessLevel.NONE)
    private Integer age;
    @Setter(AccessLevel.NONE)
    private Double bmi; // body mass index, weight in relation to height
    @Setter(AccessLevel.NONE)
    private String bmiDescription;
    @Setter(AccessLevel.NONE)
    private Integer bmr; // "rate of energy expenditure per day by endothermic animals at rest". Used Mifflin-St Jeor Equation
    @Setter(AccessLevel.NONE)
    private Double recommendedCaloricIntake=0.0;

    public ProfileDetails(Long profileId, Double height, Double weight, Integer yearOfBirth, Sex sex, Aim aim, ActivityLevel activityLevel) {
        this.profileId = profileId;
        this.height = height;
        this.weight = weight;
        this.yearOfBirth = yearOfBirth;
        this.sex = sex;
        this.aim = aim;
        this.activityLevel = activityLevel;
        setAge();
        setBmi();
        setBmr();
    }

    public ProfileDetails(ProfileDetails details) {
        this.profileId = details.getProfileId();
        this.height = details.getHeight();
        this.weight = details.getWeight();
        this.yearOfBirth = details.getYearOfBirth();
        this.sex = details.getSex();
        this.aim = details.getAim();
        this.activityLevel = details.getActivityLevel();
        setAge();
        setBmi();
        setBmr();
    }

    public ProfileDetails(Long profileId) {
        this.profileId = profileId;
    }


    public void setHeight(Double height) {
        this.height = height;
        setBmi();
        setBmr();
    }

    public void setWeight(Double weight) {
        this.weight = weight;
        setBmi();
        setBmr();
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        setAge();
    }

    public void setAge() {
        if (yearOfBirth != null) {
            this.age = Year.now().getValue() - this.yearOfBirth;
        setBmr();
        }
    }

    public void setAim(Aim aim) {
        this.aim = aim;
        setRecommendedCaloricIntake();
    }

    public void setSex(Sex sex) {
        this.sex = sex;
        setBmr();
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
        setRecommendedCaloricIntake();
    }

    private void setBmi() {
        if (this.height != null && this.height != 0 && this.weight != null) {
            this.bmi = ((double) Math.round((this.weight / Math.pow(this.height / 100, 2)) * 100)) / 100;
            setBmiDescription();
        } else {
            this.bmi = 0.0;
            this.bmiDescription = "Not enough data";
        }
    }

    public void setBmiDescription() {
        if (this.bmi < 18.5) this.bmiDescription = "Underweight";
        if (this.bmi >= 18.5 && getBmi() < 25) this.bmiDescription = "Normal weight";
        if (this.bmi >= 25 && getBmi() < 30) this.bmiDescription = "Overweight";
        if (this.bmi >= 30) this.bmiDescription = "Obesity";
    }

    public void setBmr() {
        if (sex != null && weight != null && height != null && age != null) {
            if (sex == Sex.M) this.bmr = (int) (10 * weight + 6.5 * height - 5 * age + 5);
            if (sex == Sex.F) this.bmr = (int) (10 * weight + 6.5 * height - 5 * age - 161);
            setRecommendedCaloricIntake();
        } else this.bmr = 0;
    }


    public void setRecommendedCaloricIntake() {
        if (activityLevel != null && aim != null && bmr != null) {
            double activityFactor = activityLevel.getActivityFactor();
            double aimFactor = aim.getAimFactor();
            this.recommendedCaloricIntake = Math.round((bmr * activityFactor * aimFactor) * 100.0) / 100.0;
        } else this.recommendedCaloricIntake = 0.0;
    }
}
