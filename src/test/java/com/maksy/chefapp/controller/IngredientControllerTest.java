package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @Test
    void testGetAllIngredients() throws Exception {
        Page<IngredientDTO> mockPage = new PageImpl<>(new ArrayList<>());
        when(ingredientService.getAllingredientsByCatagory(null, PageRequest.of(0, 6))).thenReturn(mockPage);

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients"))
                .andExpect(model().attributeExists("ingredients"));
    }

    @Test
    void testCreateIngredientForm() throws Exception {
        mockMvc.perform(get("/ingredients/create-ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("formAction", "/ingredients/save"))
                .andExpect(model().attribute("actionTitle", "Create Ingredient"))
                .andExpect(model().attribute("submitButtonLabel", "Create"));
    }

    @Test
    void testSaveIngredientSuccess() throws Exception {
        mockMvc.perform(post("/ingredients/save")
                        .param("name", "Carrot")
                        .param("caloriesPer100g", "41")
                        .param("category", IngredientCategory.VEGETABLE.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"));
    }

    @Test
    void testGetIngredientDetails_Found() throws Exception {
        IngredientDTO dto = new IngredientDTO();
        when(ingredientService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredientDetails"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void testGetIngredientDetails_NotFound() throws Exception {
        when(ingredientService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/ingredients/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"))
                .andExpect(flash().attribute("status", "Ingredient not found!"));
    }

    @Test
    void testEditIngredient_Found() throws Exception {
        IngredientDTO dto = new IngredientDTO();
        when(ingredientService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/ingredients/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("formAction", "/ingredients/update/1"))
                .andExpect(model().attribute("actionTitle", "Edit Ingredient"))
                .andExpect(model().attribute("submitButtonLabel", "Update"));
    }

    @Test
    void testEditIngredient_NotFound() throws Exception {
        when(ingredientService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/ingredients/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"))
                .andExpect(flash().attribute("status", "Ingredient not found!"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        mockMvc.perform(post("/ingredients/update/1")
                        .param("name", "Tomato")
                        .param("caloriesPer100g", "18")
                        .param("category", IngredientCategory.VEGETABLE.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"));
    }

    @Test
    void testDeleteIngredient_Success() throws Exception {
        mockMvc.perform(get("/ingredients/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"))
                .andExpect(flash().attribute("status", "Ingredient deleted successfully!"));
    }

    @Test
    void testDeleteIngredient_Failure() throws Exception {
        doThrow(new RuntimeException("In use")).when(ingredientService).deleteIngredient(1L);

        mockMvc.perform(get("/ingredients/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"))
                .andExpect(flash().attribute("status", "Failed to delete ingredient. It's used in dishes."));
    }
}
