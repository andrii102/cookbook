package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.mapper.IngredientMapper;
import com.maksy.chefapp.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientMapper ingredientMapper;

    // Example method to get all ingredients and map them to DTOs
    public List<IngredientDTO> getAllIngredients(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredientMapper::ingredientToIngredientDTO)  // Mapping each Ingredient to IngredientDTO
                .collect(Collectors.toList());
    }

    // Example method to save ingredient from DTO
    public Ingredient saveIngredientFromDTO(IngredientDTO ingredientDTO) {
        return ingredientMapper.ingredientDTOToIngredient(ingredientDTO);
    }
}
