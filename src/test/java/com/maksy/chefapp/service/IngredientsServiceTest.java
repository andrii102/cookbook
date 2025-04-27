package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.mapper.IngredientMapper;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientMapper ingredientMapper;

    @Mock
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllIngredients_ShouldMapIngredientsToDTOs() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        List<Ingredient> ingredients = List.of(ingredient);
        IngredientDTO ingredientDTO = new IngredientDTO();

        when(ingredientMapper.ingredientToIngredientDTO(ingredient)).thenReturn(ingredientDTO);

        // Act
        List<IngredientDTO> result = ingredientService.getAllIngredients(ingredients);

        // Assert
        assertEquals(1, result.size());
        assertEquals(ingredientDTO, result.get(0));
        verify(ingredientMapper).ingredientToIngredientDTO(ingredient);
    }

    @Test
    void findById_WhenFound_ShouldReturnIngredient() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        // Act
        Ingredient result = ingredientService.findById(1L);

        // Assert
        assertEquals(ingredient, result);
    }

    @Test
    void findById_WhenNotFound_ShouldReturnNull() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Ingredient result = ingredientService.findById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void getAllIngredientsByCategory_ShouldReturnPageOfDTOs() {
        // Arrange
        IngredientCategory category = IngredientCategory.VEGETABLE;
        Pageable pageable = PageRequest.of(0, 10);
        Ingredient ingredient = new Ingredient();
        Page<Ingredient> ingredientPage = new PageImpl<>(List.of(ingredient));

        when(ingredientRepository.findAllByCategory(category, pageable)).thenReturn(ingredientPage);
        when(ingredientMapper.ingredientToIngredientDTO(ingredient)).thenReturn(new IngredientDTO());

        // Act
        Page<IngredientDTO> result = ingredientService.getAllingredientsByCatagory(category, pageable);

        // Assert
        assertEquals(1, result.getContent().size());
        verify(ingredientRepository).findAllByCategory(category, pageable);
    }
}
