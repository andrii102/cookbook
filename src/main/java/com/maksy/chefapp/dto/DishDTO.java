package com.maksy.chefapp.dto;

import com.maksy.chefapp.model.DishIngredient;
import com.maksy.chefapp.model.enums.DishType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DishDTO {
    private Long id;
    private String name;
    private DishType type;
    private String description;
    private List<DishIngredient> dishIngredients;
    private double totalWeight;
    private double totalCalories;

    @Override
    public String toString() {
        return "DishDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", totalWeight=" + totalWeight +
                ", totalCalories=" + totalCalories +
                '}';
    }
}

