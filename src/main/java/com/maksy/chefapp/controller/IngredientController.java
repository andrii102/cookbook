package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.IngredientDTO;
import com.maksy.chefapp.model.enums.IngredientCategory;
import com.maksy.chefapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String getAllIngredients(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(required = false) IngredientCategory ingredientCategory,
                                    Model model) {
        Page<IngredientDTO> ingredientsPage = ingredientService.getAllingredientsByCatagory(ingredientCategory, PageRequest.of(page, 6));
        model.addAttribute("ingredients", ingredientsPage);
        return "ingredients";
    }

    @GetMapping("/create-ingredient")
    public String createIngredientForm(Model model) {
        model.addAttribute("ingredient", new IngredientDTO());
        model.addAttribute("actionTitle", "Create Ingredient");
        model.addAttribute("formAction", "/ingredients/save");
        return "ingredientForm";
    }

    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute("ingredient") IngredientDTO ingredientDTO,
                                 RedirectAttributes redirectAttributes) {
        try {
            ingredientService.createIngredient(ingredientDTO);
            redirectAttributes.addFlashAttribute("status", "Ingredient saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Failed to save ingredient.");
        }
        return "redirect:/ingredients";
    }

}
