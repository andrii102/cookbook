package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.exception.EntityNotFoundException;
import com.maksy.chefapp.exception.StatusCodes;
import com.maksy.chefapp.mapper.DishMapper;
import com.maksy.chefapp.model.Dish;
import com.maksy.chefapp.model.DishIngredient;
import com.maksy.chefapp.model.enums.DishType;
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


    public List<DishDTO> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        return dishMapper.dishesToDishDTOs(dishes);
    }

    public DishDTO getDishById(Long id) {
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish == null) {
            throw new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(), "Dish does not exist");
        }
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
            //  Remove associated DishIngredient entries
            // Deleting each DishIngredient entry manually
            for (DishIngredient dishIngredient : dish.getDishIngredients()) {
                entityManager.remove(dishIngredient);  // Remove the DishIngredient record explicitly
            }
            // Now delete the Dish itself
            entityManager.remove(dish);
        } else {
            throw new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(), "Dish with id " + dishId + " and its ingredients were not found.");
        }
    }


    public DishDTO updateDish(Long id, DishDTO dishDTO) {
        Dish dish = dishMapper.dishDTOToDish(dishDTO);
        dish.setId(id);
        dishRepository.save(dish);
        return dishMapper.dishToDishDTO(dish);
    }

}
