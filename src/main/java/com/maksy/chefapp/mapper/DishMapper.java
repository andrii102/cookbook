package com.maksy.chefapp.mapper;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.model.Dish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")  // Tells MapStruct to generate a Spring Bean
public interface DishMapper {
    // Method to map Dish to DishDTO
    DishDTO dishToDishDTO(Dish dish);

    // Method to map DishDTO to Dish
    Dish dishDTOToDish(DishDTO dishDTO);

    // Method to map a list of Dish to a list of DishDTO
    List<DishDTO> dishesToDishDTOs(List<Dish> dishes);

    // Method to map a list of DishDTO to a list of Dish
    List<Dish> dishDTOsToDishes(List<DishDTO> dishDTOs);
}
