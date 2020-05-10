package com.damgor.foodapp.model.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Intolerance {
    Dairy, Egg, Gluten, Grain, Peanut, Seafood, Sesame, Shellfish, Soy, Sulfite, Tree_Nut("Tree Nut"), Wheat;

    private final String displayName;
    private final Map<String, String> displayNameMap = new HashMap<>();


    Intolerance(String displayName) {
        this.displayName = displayName;
    }
    Intolerance() {
        this.displayName = name();
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<String, String> getDisplayNameMap() {
        Arrays.asList(Intolerance.values()).forEach(k -> displayNameMap.put(k.toString(), k.getDisplayName()));
        return displayNameMap;
    }


}
