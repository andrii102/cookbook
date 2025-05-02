package com.maksy.chefapp.model;

import com.maksy.chefapp.model.enums.DishType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Dish")
@Getter
@Setter
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DishType type;

    @Column(name = "description")
    private String description;

    @Column(name = "total_calories")  // Store the total calories in the DB
    private double totalCalories;

    @Column(name = "total_weight")  // Store the total weight in the DB
    private double totalWeight;


    @OneToMany(mappedBy = "dishId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishIngredient> dishIngredients;

    private static final Logger logger = LoggerFactory.getLogger(Dish.class);

    public Dish(String name, DishType type) {
        this.name = name;
        this.type = type;
        logger.info("Блюдо '{}' типу '{}' створено", name, type);
    }

    public Dish() {

    }


    public void removeIngredients(List<Long> ingredientIds) {
        System.out.println("REMOVING INGREDIENTS, ARGUMENTS:"+ingredientIds);
        System.out.println("Before removing dishIngredients size: " +  dishIngredients.size());
        this.dishIngredients.removeIf(d -> ingredientIds.contains(d.getId()));
        System.out.println("After removing dishIngredients size: " +  dishIngredients.size());;
    }

    public void addIngredients(List<Long> ingredientIds) {}

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + Objects.toString(name, "null") + '\'' +
                ", type=" + Objects.toString(type, "null") +
                ", description='" + Objects.toString(description, "null") + '\'' +
                ", totalCalories=" + totalCalories +
                ", totalWeight=" + totalWeight +
                ", dishIngredients=" + (dishIngredients != null ? dishIngredients : "[]") +
                '}';
    }

}
