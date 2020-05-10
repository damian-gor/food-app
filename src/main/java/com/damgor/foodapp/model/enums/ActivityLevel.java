package com.damgor.foodapp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://www.integrativepro.com/Resources/Integrative-Blog/2016/How-to-Determine-Caloric-Intake-Needs
@Getter
@AllArgsConstructor
public enum ActivityLevel {
    ANY_OR_LITTLE("Any or little", "Any or little", 1.2),
    LIGHT_EXERCISE("Light exercise", "1-3 days per week", 1.375),
    MODERATE_EXERCISE("Moderate exercise", "3-5 days per week", 1.55),
    HEAVY_EXERCISE("Heavy exercise", " 6-7 days per week", 1.725),
    VERY_HEAVY_EXERCISE("Very heavy exercise", "Very heavy workout each day / physical job", 1.9);

    private final String displayName;
    private final String description;
    private final double activityFactor;
    //    private final Map <String, String> map = new HashMap<>();
//
//
//    public Map<String, String> getMap() {
//        Arrays.asList(ActivityLevel.values()).forEach(k -> map.put(k.toString(), k.getDescription()));
//        return map;
//    }
    private final Map<String, String> displayNameMap = new HashMap<>();
    private final Map<String, String> descriptionMap = new HashMap<>();

    public Map<String, String> getDisplayNameMap() {
        Arrays.asList(ActivityLevel.values()).forEach(k -> displayNameMap.put(k.toString(), k.getDisplayName()));
        return displayNameMap;
    }
    public Map<String, String> getDescriptionMap() {
        Arrays.asList(ActivityLevel.values()).forEach(k -> descriptionMap.put(k.toString(), k.getDescription()));
        return descriptionMap;
    }

}