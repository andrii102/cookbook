package com.maksy.chefapp.controller;

import com.maksy.chefapp.dto.DishDTO;
import com.maksy.chefapp.model.Dish;
import com.maksy.chefapp.service.DishService; // Assume you have a service to fetch data
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DishService dishService;

    @GetMapping("/")
    public String index(Model model) {
        List<DishDTO> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "index";
    }
}
