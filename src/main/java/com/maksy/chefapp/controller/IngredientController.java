package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.IngredientDTO;

import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public String getAllIngredients(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(required = false) IngredientCategory ingredientCategory,
                                    Model model) {
        Page<IngredientDTO> ingredientsPage = ingredientService.getAllingredientsByCatagory(ingredientCategory, PageRequest.of(page, 6));
        model.addAttribute("ingredients", ingredientsPage);
        return "ingredients";
    }

}
