package com.maksy.chefapp.service;

import com.maksy.chefapp.model.Dish;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.DishType;
import com.maksy.chefapp.model.enums.IngredientCategory;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ChefService {
    private final Logger logger = LoggerFactory.getLogger(ChefService.class);
    @Getter
    private List<Dish> savedDishes;
    @Getter
    private List<Ingredient> availableIngredients;
    private String ingredientsFilePath = "ingredients.txt";
    private static final Pattern CALORIE_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

    public ChefService() {
        availableIngredients = new ArrayList<>();
        savedDishes = new ArrayList<>();
        logger.info("ChefService created");
    }

    public void initializeIngredients() throws IOException {
        logger.info("Initializing ingredients from file");
        try (BufferedReader reader = new BufferedReader(new FileReader(ingredientsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String type = parts[0].trim();
                    String name = parts[1].trim();
                    double caloriesPer100g = Double.parseDouble(parts[2].trim());

                    // Use the Ingredient class and IngredientCategory enum to set category
                    IngredientCategory category = getIngredientCategory(type);
                    if (category != null) {
                        availableIngredients.add(new Ingredient(name, caloriesPer100g, category));
                    } else {
                        logger.warn("Unknown ingredient type: {}", type);
                    }
                } else {
                    logger.warn("Incorrect line format: {}", line);
                }
            }
            logger.info("Ingredients initialized successfully");
            initializePreMadeDishes();
        } catch (IOException e) {
            logger.warn("Error reading ingredient file", e);
        } catch (NumberFormatException e) {
            logger.warn("Invalid calorie format in ingredient file", e);
        }
    }

    private IngredientCategory getIngredientCategory(String type) {
        switch (type) {
            case "Vegetable":
                return IngredientCategory.VEGETABLE;
            case "Meat":
                return IngredientCategory.MEAT;
            case "Spice":
                return IngredientCategory.SPICE;
            case "Dairy":
                return IngredientCategory.DAIRY;
            default:
                return null;
        }
    }

    public void initializePreMadeDishes() {
        if (availableIngredients.isEmpty()) {
            logger.info("Cannot create premade dishes, ingredient list is empty");
            return;
        }

        logger.info("Initializing premade dishes");

        // Greek Salad
        Dish greekSalad = new Dish("Greek Salad", DishType.SALAD);
        greekSalad.addIngredient(availableIngredients.get(0), 160);  // Tomatoes
        greekSalad.addIngredient(availableIngredients.get(2), 110);  // Cucumbers
        greekSalad.addIngredient(availableIngredients.get(3), 90);   // Sweet Pepper
        greekSalad.addIngredient(availableIngredients.get(6), 40);   // Olive Oil
        greekSalad.addIngredient(availableIngredients.get(10), 250); // Feta
        savedDishes.add(greekSalad);
        logger.info("Added premade dish: Greek Salad");

        // Caesar Salad
        Dish caesarSalad = new Dish("Caesar Salad", DishType.SALAD);
        caesarSalad.addIngredient(availableIngredients.get(1), 80);  // Cherry Tomatoes
        caesarSalad.addIngredient(availableIngredients.get(8), 150); // Chicken Fillet
        caesarSalad.addIngredient(availableIngredients.get(4), 150); // Iceberg Lettuce
        caesarSalad.addIngredient(availableIngredients.get(6), 30);  // Olive Oil
        caesarSalad.addIngredient(availableIngredients.get(11), 20); // Parmesan
        savedDishes.add(caesarSalad);
        logger.info("Added premade dish: Caesar Salad");
    }
}
