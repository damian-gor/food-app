package com.damgor.foodapp.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum Aim {
    FAST_WEIGHT_LOSS("Fast weight loss", 0.9),
    WEIGHT_LOSS ("Weight loss", 0.95),
    KEEP_WEIGHT ("Keep weight", 1),
    WEIGHT_GAIN ("Weight gain", 1.05),
    FAST_WEIGHT_GAIN ("Fast weight gain", 1.1);

    private final String displayName;
    private final String description;
    private final double aimFactor;

    Aim(String displayName, double aimFactor) {
        this.displayName = displayName;
        this.description = "Kcal intake at " + (int)(aimFactor*100) + "% of the standard level";
        this.aimFactor = aimFactor;
    }

    private final Map<String, String> displayNameMap = new HashMap<>();
    private final Map<String, String> descriptionMap = new HashMap<>();

    public Map<String, String> getDisplayNameMap() {
        Arrays.asList(Aim.values()).forEach(k -> displayNameMap.put(k.toString(), k.getDisplayName()));
        return displayNameMap;
    }
    public Map<String, String> getDescriptionMap() {
        Arrays.asList(Aim.values()).forEach(k -> descriptionMap.put(k.toString(), k.getDescription()));
        return descriptionMap;
    }

}
