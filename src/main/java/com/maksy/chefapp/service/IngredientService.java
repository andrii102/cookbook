package com.maksy.chefapp.service;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.mapper.IngredientMapper;
import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class IngredientService {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private IngredientRepository ingredientRepository;

    //Method to get all ingredients and map them to DTOs
    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(ingredientMapper::ingredientToIngredientDTO)
                .collect(toList());
    }

    public Ingredient findById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElse(null);
    }

    public List<IngredientDTO> findAllById(List<Long>  ingredientIds) {
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        return ingredients.stream()
                .map(ingredientMapper::ingredientToIngredientDTO)
                .toList();
    }

    public Page<IngredientDTO> getAllingredientsByCatagory(IngredientCategory ingredientCategory, Pageable pageable) {
        Page<Ingredient> ingredientsPage = ingredientRepository.findAllByCategory(ingredientCategory, pageable);
        List<Ingredient> ingredients = ingredientsPage.getContent();
        System.out.println(ingredients);
        List<IngredientDTO> ingredientsDTO = ingredientsPage.getContent().stream()
                .map(ingredientMapper::ingredientToIngredientDTO)
                .toList();
        System.out.println("Mapped ingredients: " + ingredientsDTO);

        return new PageImpl<>(ingredientsDTO, pageable, ingredientsPage.getTotalElements());
    }
}
