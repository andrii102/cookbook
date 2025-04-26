package com.maksy.chefapp.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;


@Getter
@Setter
public class IngredientEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    public IngredientEntry(Ingredient ingredient, double weight) {
        this.ingredient = ingredient;
        this.weight = weight;
    }

    public IngredientEntry() {

    }
}
