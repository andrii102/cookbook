package com.maksy.chefapp.repository;

import com.maksy.chefapp.model.Ingredient;
import com.maksy.chefapp.model.enums.IngredientCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findById(Long id);

    @Query("SELECT i FROM Ingredient i WHERE (:ingredientCategory IS NULL OR i.category = :ingredientCategory) ")
    Page<Ingredient> findAllByCategory(@Param("ingredientCategory") IngredientCategory ingredientCategory, Pageable pageable);
}
