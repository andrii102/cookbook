package com.maksy.chefapp.service;

import com.maksy.chefapp.model.DishIngredient;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.repository.DishIngredientRepository;
import com.maksy.chefapp.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DishIngredientService {
    @Autowired
    private DishIngredientRepository dishIngredientRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private DishRepository dishRepository;

    public List<DishIngredient> findAllByDishId(Long dishId){
        return dishIngredientRepository.findAllByDishId(dishId);
    }

    public  List<DishIngredient> findAll(){
        return dishIngredientRepository.findAll();
    }

    public List<DishIngredient> extractDishIngredientsFromParams(Map<String, String> params, Long dish) {
        List<DishIngredient> dishIngredients = new ArrayList<>();

        int index = 0;
        while (true) {
            String ingredientIdStr = params.get("dishIngredients[" + index + "].ingredientId");
            String weightStr = params.get("dishIngredients[" + index + "].weight");

            if (ingredientIdStr == null || weightStr == null) {
                break;
            }

            Long ingredientId = Long.parseLong(ingredientIdStr);
            double weight = Double.parseDouble(weightStr);

            // Fetch the Ingredient entity by ID
            Ingredient ingredient = ingredientService.findById(ingredientId);
            if (ingredient == null) {
                index++;
                continue; // or throw exception if you prefer
            }

            DishIngredient dishIngredient = new DishIngredient();

            dishIngredient.setDish(dishRepository.findById(dish).orElse(null)); // set full Dish object
            dishIngredient.setIngredient(ingredient); // set full Ingredient object
            dishIngredient.setWeight(weight);

            dishIngredients.add(dishIngredient);

            index++;
        }

        return dishIngredients;
    }

    public void updateDishIngredients(Long id, List<DishIngredient> dishIngredients) {
    }

    public void deleteByDishId(Long id) {
        dishIngredientRepository.deleteById(id);
    }
}
