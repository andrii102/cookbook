package com.maksy.chefapp.model;

import com.maksy.chefapp.model.enums.IngredientCategory;
import lombok.Getter;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Ingredient")
@Getter
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "calories_per_100g")
    private double caloriesPer100g;

    @Enumerated(EnumType.STRING)  // Use EnumType.STRING to store the enum value as a string
    @Column(name = "category")  // This will store the enum value in the 'category' column
    private IngredientCategory category;

    public Ingredient(String name, double caloriesPer100g, IngredientCategory category) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (caloriesPer100g <= 0) {
            throw new IllegalArgumentException("Calories per 100g must be greater than zero.");
        }
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.category = category;
    }

    public Ingredient() {

    }

    public double getCalories(double weight) {
        return caloriesPer100g * weight / 100;
    }



    @Override
    public String toString() {
        return String.format("%-15s | Калорійність: %6.2f ккал на 100 г", name, caloriesPer100g);
    }
}
