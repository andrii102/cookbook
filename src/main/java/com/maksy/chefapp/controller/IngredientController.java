package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public List<IngredientDTO> getAllIngredients() {
        // Assuming ingredientService returns a list of ingredients
        List<Ingredient> ingredients = getIngredientsFromDatabase();  // Replace with actual DB call
        return ingredientService.getAllIngredients(ingredients);
    }

    @GetMapping("/ingredients/{id}")
    public IngredientDTO getIngredientById(@PathVariable Long id) {
        Ingredient ingredient = getIngredientByIdFromDatabase(id);  // Replace with actual DB call
        return ingredientService.getAllIngredients(List.of(ingredient)).get(0);
    }

    private List<Ingredient> getIngredientsFromDatabase() {
        // Placeholder for actual database call
        return List.of(new Ingredient("Tomato", 19, IngredientCategory.VEGETABLE));
    }

    private Ingredient getIngredientByIdFromDatabase(Long id) {
        // Placeholder for actual database call
        return new Ingredient("Tomato", 19, IngredientCategory.VEGETABLE);
    }
}
