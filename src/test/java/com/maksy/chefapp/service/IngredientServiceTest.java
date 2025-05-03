package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.exception.EntityNotFoundException;
import com.maksy.chefapp.mapper.IngredientMapper;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIngredients() {
        List<Ingredient> ingredients = List.of(new Ingredient(), new Ingredient());
        when(ingredientRepository.findAll()).thenReturn(ingredients);
        when(ingredientMapper.ingredientToIngredientDTO(any())).thenReturn(new IngredientDTO());

        List<IngredientDTO> result = ingredientService.getAllIngredients();

        assertEquals(2, result.size());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Found() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.ingredientToIngredientDTO(any())).thenReturn(new IngredientDTO());

        IngredientDTO result = ingredientService.findById(1L);

        assertNotNull(result);
        verify(ingredientRepository).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ingredientService.findById(1L));
    }

    @Test
    void testFindAllById() {
        List<Ingredient> ingredients = List.of(new Ingredient(), new Ingredient());
        when(ingredientRepository.findAllById(anyList())).thenReturn(ingredients);
        when(ingredientMapper.ingredientToIngredientDTO(any())).thenReturn(new IngredientDTO());

        List<IngredientDTO> result = ingredientService.findAllById(List.of(1L, 2L));

        assertEquals(2, result.size());
        verify(ingredientRepository).findAllById(anyList());
    }

    @Test
    void testGetAllIngredientsByCategory() {
        IngredientCategory category = IngredientCategory.VEGETABLE;
        Ingredient ingredient = new Ingredient();
        Page<Ingredient> page = new PageImpl<>(List.of(ingredient));

        when(ingredientRepository.findAllByCategory(eq(category), any(Pageable.class)))
                .thenReturn(page);
        when(ingredientMapper.ingredientToIngredientDTO(any()))
                .thenReturn(new IngredientDTO());

        Pageable pageable = PageRequest.of(0, 10);
        Page<IngredientDTO> result = ingredientService.getAllingredientsByCatagory(category, pageable);

        assertEquals(1, result.getContent().size());
        verify(ingredientRepository).findAllByCategory(eq(category), eq(pageable));
    }
}
