package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @Test
    void testGetAllIngredients() throws Exception {
        // Mock the service behavior
        Page<IngredientDTO> mockPage = new PageImpl<>(new ArrayList<>());
        when(ingredientService.getAllingredientsByCatagory(null, PageRequest.of(0, 6))).thenReturn(mockPage);

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients"))
                .andExpect(model().attributeExists("ingredients"));
    }
}
