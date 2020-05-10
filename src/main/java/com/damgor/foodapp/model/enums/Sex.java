package com.damgor.foodapp.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum Sex {
    M ("Male"), F ("Female");

    private final String displayName;
    private final Map<String, String> displayNameMap = new HashMap<>();

    Sex(String displayName) {
        this.displayName = displayName;
    }



    public Map<String, String> getDisplayNameMap() {
        Arrays.asList(Sex.values()).forEach(k -> displayNameMap.put(k.toString(), k.getDisplayName()));
        return displayNameMap;
    }
}
