package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.exception.EntityNotFoundException;
import com.maksy.chefapp.exception.StatusCodes;
import com.maksy.chefapp.mapper.DishMapper;
import com.maksy.chefapp.model.Dish;
import com.maksy.chefapp.model.DishIngredient;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.DishType;
import com.maksy.chefapp.repository.DishIngredientRepository;
import com.maksy.chefapp.repository.DishRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DishIngredientRepository dishIngredientRepository;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private DishIngredientService dishIngredientService;

    public List<DishDTO> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        System.out.println(dishMapper.dishesToDishDTOs(dishes).toString());
        return dishMapper.dishesToDishDTOs(dishes);
    }

    public DishDTO getDishById(Long id) {
        Dish dish = dishRepository.findById(id).orElse(null);
        return dishMapper.dishToDishDTO(dish);
    }

    public Page<DishDTO> getAllDishesFiltered(DishType dishType, Pageable pageable) {
        Page<Dish> dishesPage = dishRepository.findFilteredDishes(dishType, pageable);
        List<DishDTO> dishesDTO = dishesPage.getContent().stream()
                .map(dishMapper::dishToDishDTO)
                .toList();

        return new PageImpl<>(dishesDTO, pageable, dishesPage.getTotalElements());
    }

    public DishDTO createDish(DishDTO dishDTO) {
        Dish dish = dishMapper.dishDTOToDish(dishDTO);
        dishRepository.save(dish);
        return dishMapper.dishToDishDTO(dish);
    }

    @Transactional
    public void deleteDish(Long dishId) {
        // Fetch the Dish entity using the dishId
        Dish dish = entityManager.find(Dish.class, dishId);

        if (dish != null) {
            // Step 1: Remove associated DishIngredient entries
            // Deleting each DishIngredient entry manually
            for (DishIngredient dishIngredient : dish.getDishIngredients()) {
                entityManager.remove(dishIngredient);  // Remove the DishIngredient record explicitly
            }

            // Step 2: Now delete the Dish itself
            entityManager.remove(dish);  // Remove the Dish entity

            // Log successful deletion
            System.out.println("Dish with id " + dishId + " and its ingredients were deleted.");
        } else {
            // Handle the case where the Dish doesn't exist
            System.out.println("Dish with id " + dishId + " does not exist.");
        }
    }


    public DishDTO updateDish(Long id, DishDTO dishDTO) {
        Dish dish = dishMapper.dishDTOToDish(dishDTO);
        dish.setId(id);
        dishRepository.save(dish);
        return dishMapper.dishToDishDTO(dish);
    }

//    @Transactional
//    public void updateDish(Long id, DishDTO dishDTO) {
//        Dish dish = dishRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(), "Dish not found"));
//
//        dish.setName(dishDTO.getName());
//        dish.setType(dishDTO.getType());
//        dish.setDescription(dishDTO.getDescription());
//        dish.setTotalCalories(dishDTO.getTotalCalories());
//        dish.setTotalWeight(dishDTO.getTotalWeight());
//        dishRepository.save(dish);
//
//        dishIngredientService.deleteByDishId(dish.getId());
//
//        // Save new dishIngredients
//        for (DishIngredient dishIngredientDTO : dishDTO.getDishIngredients()) {
//            DishIngredient dishIngredient = new DishIngredient();
//            dishIngredient.setDish(dish); // link to this dish
//            Ingredient ingredient = ingredientService.findById(dishIngredient.getIngredient().getId());
//            dishIngredient.setIngredient(ingredient);
//            dishIngredient.setWeight(dishIngredientDTO.getWeight());
//            dishIngredientRepository.save(dishIngredient);
//        }
//    }

}
