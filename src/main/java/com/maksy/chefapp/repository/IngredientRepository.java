package com.maksy.chefapp.repository;

import com.maksy.chefapp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    // You can add custom queries here, for example:
    // List<Ingredient> findByType(String type);
}
