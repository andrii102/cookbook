package com.maksy.chefapp.mapper;

import com.maksy.chefapp.dto.DishIngredientDTO;
import com.maksy.chefapp.model.DishIngredient;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DishIngredientMapper {

    DishIngredientDTO toDishIngredientDTO(DishIngredient dishIngredient);

    DishIngredient toDishIngredient(DishIngredientDTO dishIngredientDTO);

}
