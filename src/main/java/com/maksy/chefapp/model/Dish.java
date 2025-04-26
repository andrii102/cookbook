package com.maksy.chefapp.model;

import com.maksy.chefapp.model.enums.DishType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Dish")
@Getter
@Setter
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DishType type;

    @Column(name = "description")
    private String description;

    @Column(name = "total_calories")  // Store the total calories in the DB
    private double totalCalories;

    @Column(name = "total_weight")  // Store the total weight in the DB
    private double totalWeight;


    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<DishIngredient> dishIngredients;

    private static final Logger logger = LoggerFactory.getLogger(Dish.class);

    public Dish(String name, DishType type) {
        this.name = name;
        this.type = type;
        logger.info("Блюдо '{}' типу '{}' створено", name, type);
    }

    public Dish() {

    }

    public void addIngredient(Ingredient ingredient, double weight) {
        DishIngredient dishIngredient = new DishIngredient();
        dishIngredient.setDish(this);
        dishIngredient.setIngredient(ingredient);
        dishIngredient.setWeight(weight);

        this.dishIngredients.add(dishIngredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        this.dishIngredients.removeIf(d -> d.getIngredient().equals(ingredient));
    }

    // Method to delete Dish and its associated DishIngredient records
    public void deleteDishWithIngredients(EntityManager entityManager) {
        // First, we remove all DishIngredient records
        for (DishIngredient dishIngredient : dishIngredients) {
            entityManager.remove(dishIngredient);  // Removing the DishIngredient explicitly
        }
        // Now, we remove the Dish itself
        entityManager.remove(this);  // Removing the Dish
    }

    @Override
    public String toString() {
        // Build a string representation of the dish, including ingredient details
        String ingredientDetails = dishIngredients.stream()
                .map(entry -> entry.getIngredient().getName() + " " + entry.getWeight() + " г")
                .collect(Collectors.joining(", "));

        // Return a formatted string with the dish details
        return String.format("%-15s | Тип: %-10s | Загальна вага: %5.2f г | Загальна калорійність: %8.2f ккал (%s)",
                name, type, totalWeight, totalCalories, ingredientDetails);
    }

}
