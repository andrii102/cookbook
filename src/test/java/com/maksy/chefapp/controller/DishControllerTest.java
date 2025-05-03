package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.service.DishIngredientService;
import com.maksy.chefapp.service.DishService;
import com.maksy.chefapp.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DishService dishService;

    @InjectMocks
    private DishController dishController;

    @Mock
    private DishIngredientService dishIngredientService;

    @Mock
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();
    }

    @Test
    void testShowAllDishes() throws Exception {
        mockMvc.perform(get("/dishes/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("dishes"));
    }

    @Test
    void testShowDishById() throws Exception {
        // Simulate dish found
        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(1L);
        dishDTO.setName("Salad");

        Mockito.when(dishService.getDishById(1L)).thenReturn(dishDTO);

        mockMvc.perform(get("/dishes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("dishDetails"))
                .andExpect(model().attribute("dish", dishDTO));
    }

    @Test
    void testShowDishById_NotFound() throws Exception {
        // Simulate dish not found
        Mockito.when(dishService.getDishById(999L)).thenReturn(null);

        mockMvc.perform(get("/dishes/999"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", "Dish not found"));
    }

    @Test
    void testDeleteDishById() throws Exception {
        // Simulate successful delete
        Mockito.doNothing().when(dishService).deleteDish(1L);

        mockMvc.perform(get("/dishes/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dishes/all"))
                .andExpect(flash().attribute("status", "Dish deleted successfully!"));
    }

    @Test
    void testDeleteDishById_Fail() throws Exception {
        // Simulate error while deleting
        Mockito.doThrow(new RuntimeException("Failed to delete")).when(dishService).deleteDish(999L);

        mockMvc.perform(get("/dishes/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dishes/all"))
                .andExpect(flash().attribute("status", "Failed to delete dish. Please try again."));
    }

    @Test
    void testEditDishById() throws Exception {
        // Simulate dish found
        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(1L);
        dishDTO.setName("Salad");

        Mockito.when(dishService.getDishById(1L)).thenReturn(dishDTO);

        mockMvc.perform(get("/dishes/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editDish"))
                .andExpect(model().attribute("dish", dishDTO))
                .andExpect(model().attribute("actionTitle", "Edit dish"))
                .andExpect(model().attribute("formAction", "/dishes/update/1"));
    }

    @Test
    void testEditDishById_NotFound() throws Exception {
        // Simulate dish not found
        Mockito.when(dishService.getDishById(999L)).thenReturn(null);

        mockMvc.perform(get("/dishes/edit/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dishes/all"))
                .andExpect(flash().attribute("status", "Dish not found!"));
    }

    @Test
    void testSaveDish() throws Exception {
        DishDTO dishDTO = new DishDTO();
        dishDTO.setName("New Dish");

        Mockito.when(dishService.createDish(Mockito.any(DishDTO.class))).thenReturn(dishDTO);

        mockMvc.perform(post("/dishes/save")
                        .param("name", "New Dish"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dishes/all"))
                .andExpect(flash().attribute("status", "Dish saved successfully!"));
    }

    @Test
    void testAddDish() throws Exception {
        mockMvc.perform(get("/dishes/create-dish"))
                .andExpect(status().isOk())
                .andExpect(view().name("editDish"));
    }
}
