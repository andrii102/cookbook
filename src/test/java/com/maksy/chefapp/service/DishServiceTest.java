package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.dto.DishIngredientDTO;
import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.exception.EntityNotFoundException;
import com.maksy.chefapp.mapper.DishMapper;
import com.maksy.chefapp.model.Dish;
import com.maksy.chefapp.model.DishIngredient;
import com.maksy.chefapp.model.enums.DishType;
import com.maksy.chefapp.repository.DishRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {

    @InjectMocks
    private DishService dishService;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @Mock
    private DishIngredientService dishIngredientService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDishes_ShouldReturnDishDTOList() {
        List<Dish> dishes = List.of(new Dish("Caesar Salad", DishType.SALAD));
        List<DishDTO> dishDTOs = List.of(new DishDTO());
        when(dishRepository.findAll()).thenReturn(dishes);
        when(dishMapper.dishesToDishDTOs(dishes)).thenReturn(dishDTOs);

        List<DishDTO> result = dishService.getAllDishes();

        assertEquals(dishDTOs, result);
        verify(dishRepository).findAll();
        verify(dishMapper).dishesToDishDTOs(dishes);
    }

    @Test
    void getDishById_WhenExists_ShouldReturnDishDTO() {
        Dish dish = new Dish("Borscht", DishType.SOUP);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        DishDTO dishDTO = new DishDTO();
        when(dishMapper.dishToDishDTO(dish)).thenReturn(dishDTO);

        DishDTO result = dishService.getDishById(1L);

        assertEquals(dishDTO, result);
    }

    @Test
    void getDishById_WhenNotFound_ShouldThrowEntityNotFoundException() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> dishService.getDishById(1L));
    }

    @Test
    void getAllDishesFiltered_ShouldReturnFilteredPage() {
        DishType dishType = DishType.MAIN;
        Pageable pageable = PageRequest.of(0, 10);
        List<Dish> dishes = List.of(new Dish("Steak", DishType.MAIN));
        Page<Dish> dishesPage = new PageImpl<>(dishes);

        when(dishRepository.findFilteredDishes(dishType, pageable)).thenReturn(dishesPage);

        List<DishDTO> dishDTOs = List.of(new DishDTO());
        when(dishMapper.dishToDishDTO(any())).thenReturn(dishDTOs.get(0));

        Page<DishDTO> result = dishService.getAllDishesFiltered(dishType, pageable);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void createDish_ShouldSaveAndReturnDishDTO() {
        DishDTO dishDTO = new DishDTO();
        Dish dish = new Dish("Cake", DishType.DESSERT);

        when(dishMapper.dishDTOToDish(dishDTO)).thenReturn(dish);
        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishMapper.dishToDishDTO(dish)).thenReturn(dishDTO);

        DishDTO result = dishService.createDish(dishDTO);

        assertEquals(dishDTO, result);
        verify(dishRepository).save(dish);
    }

    @Test
    void deleteDish_WhenDishExists_ShouldDeleteDishAndIngredients() {
        DishIngredient ingredient = new DishIngredient();
        Dish dish = new Dish();
        dish.setDishIngredients(List.of(ingredient));

        when(entityManager.find(Dish.class, 1L)).thenReturn(dish);

        dishService.deleteDish(1L);

        verify(entityManager).remove(ingredient);
        verify(entityManager).remove(dish);
    }

    @Test
    void deleteDish_WhenDishNotFound_ShouldThrowEntityNotFoundException() {
        when(entityManager.find(Dish.class, 1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> dishService.deleteDish(1L));
    }

    @Test
     void updateDish_ShouldUpdateDishAndRecalculateCalories() {
        // Arrange
        Long dishId = 1L;
        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(dishId);
        Dish dish = new Dish();
        dish.setId(dishId);

        DishIngredient newIngredient = new DishIngredient(1L, 2L, 150); // New ingredient (id == null)
        dish.setDishIngredients(List.of(newIngredient));

        when(dishMapper.dishDTOToDish(dishDTO)).thenReturn(dish);
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);
        when(dishMapper.dishToDishDTO(dish)).thenReturn(dishDTO);

        // Mock the behavior of dishIngredientService and ingredientService
        DishIngredientDTO dishIngredientDTO = new DishIngredientDTO();
        dishIngredientDTO.setIngredientId(2L);
        dishIngredientDTO.setWeight(150);
        List<DishIngredientDTO> dishIngredientDTOList = List.of(dishIngredientDTO);

        when(dishIngredientService.findAllByDishId(dishId)).thenReturn(dishIngredientDTOList);

        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(2L);
        ingredientDTO.setCaloriesPer100g(200.0);
        when(ingredientService.findAllById(List.of(2L))).thenReturn(List.of(ingredientDTO));

        // Act
        DishDTO result = dishService.updateDish(dishId, dishDTO, null);

        // Assert
        assertEquals(dishDTO, result);
        verify(dishRepository, atLeastOnce()).save(dish);
        verify(dishIngredientService).findAllByDishId(dishId);
        verify(ingredientService).findAllById(List.of(2L));

        assertEquals(150, dish.getTotalWeight());
        assertEquals(300, dish.getTotalCalories()); // 200 per 100g × 150g = 300
    }


}
