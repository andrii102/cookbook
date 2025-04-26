package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.model.Dish;
import com.maksy.chefapp.model.enums.DishType;
import com.maksy.chefapp.service.DishIngredientService;
import com.maksy.chefapp.service.DishService;
import com.maksy.chefapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishIngredientService dishIngredientService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/dishes")
    public String showAllDishes(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(required = false) DishType dishType,
                                Model model) {
        Page<DishDTO> dishesPage = dishService.getAllDishesFiltered(dishType, PageRequest.of(page, 6));
        model.addAttribute("dishes", dishesPage);
        return "dishes";
    }

    // Display a specific dish by ID
    @GetMapping("/dishes/{id}")
    public String showDishById(@PathVariable("id") long id, Model model) {
        DishDTO dishDTO = dishService.getDishById(id);
        if (dishDTO != null) {
            model.addAttribute("dish", dishDTO);
            return "dishDetails"; // Render the dishDetails.html template
        } else {
            model.addAttribute("error", "Dish not found");
            return "error"; // Render error.html template
        }
    }

    @GetMapping("/dishes/delete/{id}")
    public String deleteDishById(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            dishService.deleteDish(id);
            redirectAttributes.addFlashAttribute("status", "Dish deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Failed to delete dish. Please try again.");
        }
        return "redirect:/dishes";
    }

    @GetMapping("/dishes/edit/{id}")
    public String editDishById(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        DishDTO dishDTO = dishService.getDishById(id);

        if (dishDTO!=null) {
            model.addAttribute("dish", dishDTO);
            model.addAttribute("actionTitle", "Edit dish");
            model.addAttribute("formAction", "/dishes/update/{id}(id=${dish.id})");
            model.addAttribute("formTitle", "Edit Dish");
            return "editDish";
        } else {
            redirectAttributes.addFlashAttribute("status", "Dish not found!");
            return "redirect:/dishes";
        }
    }

    @PostMapping("/dishes/update/{id}")
    public String updateDish(@PathVariable("id") long id,
                             @ModelAttribute("dish") DishDTO dishDTO,
                             RedirectAttributes redirectAttributes) {
        dishService.updateDish(id, dishDTO);
        redirectAttributes.addFlashAttribute("status", "Dish updated successfully!");
        return "redirect:/dishes";
    }

    @PostMapping("dishes/save")
    public String saveDish(@ModelAttribute("dishDTO") DishDTO dishDTO, RedirectAttributes redirectAttributes) {
        dishService.createDish(dishDTO);
        redirectAttributes.addFlashAttribute("status", "Dish saved successfully!");
        return "redirect:/dishes";
    }


    @GetMapping("dishes/add-dish")
    public String addDish(Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("dish", new Dish());
            model.addAttribute("actionTitle", "Create dish");
            model.addAttribute("formAction", "/dishes/save");
            model.addAttribute("formTitle", "Create Dish");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Failed to add dish. Please try again.");
        }
        return "editDish";
    }

}
