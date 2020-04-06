package com.damgor.foodapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
public class EdamamOutput {
    private List<Parsed> parsed;

    @Data
    public static class Parsed {
        private Food food;

        @Data
        public static class Food {
            @Id
            private String foodId;
            private String label;
            private Nutrients nutrients;

            @Data
            public static class Nutrients {
                @JsonAlias({"PROCNT"})
                private Double protein;
                @JsonAlias({"FAT"})
                private Double fat;
                @JsonAlias({"CHOCDF"})
                private Double carbs;
                @JsonAlias({"ENERC_KCAL"})
                private Double kcal;
            }
        }
    }
}




