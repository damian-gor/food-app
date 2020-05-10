package com.damgor.foodapp.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://spoonacular.com/food-api/docs#Cuisines
@Getter
public enum Cuisine {
    African, American, British, Cajun, Caribbean, Chinese, Eastern_European("Eastern European"), European,
    French, German, Greek, Indian, Irish, Italian, Japanese, Jewish, Korean, Latin_American ("Latin American"),
    Mediterranean, Mexican, Middle_Eastern ("Middle Eastern"), Nordic, Southern, Spanish, Thai, Vietnamese;

    private final String displayName;
    private final Map<String, String> displayNameMap = new HashMap<>();

    Cuisine(String displayName) {
        this.displayName = displayName;
    }

    Cuisine() {
        this.displayName = name();
    }

    public Map<String, String> getDisplayNameMap() {
        Arrays.asList(Cuisine.values()).forEach(k -> displayNameMap.put(k.toString(), k.getDisplayName()));
        return displayNameMap;
    }
}
