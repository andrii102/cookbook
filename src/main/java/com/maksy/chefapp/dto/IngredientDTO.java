package com.maksy.chefapp.dto;

import com.maksy.chefapp.model.enums.IngredientCategory;
import lombok.Data;

@Data
public class IngredientDTO {
    private Long id;
    private String name;
    private double caloriesPer100g;
    private IngredientCategory category;
}
