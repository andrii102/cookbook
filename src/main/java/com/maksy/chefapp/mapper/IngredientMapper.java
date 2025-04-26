package com.maksy.chefapp.mapper;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")  // Tells MapStruct to generate a Spring Bean
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    // Method to map Ingredient to IngredientDTO
    IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    // Method to map IngredientDTO to Ingredient
    Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO);
}
