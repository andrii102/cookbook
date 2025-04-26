-- Table for storing ingredient categories
CREATE TABLE IngredientCategory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Table for storing ingredients
CREATE TABLE Ingredient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    calories_per_100g INT NOT NULL,
    category VARCHAR(255) ,
    FOREIGN KEY (category) REFERENCES IngredientCategory(name)
);

-- Table for storing dishes
CREATE TABLE Dish (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      type VARCHAR(255) NOT NULL,
      description TEXT,
      total_calories DOUBLE PRECISION,
      total_weight DOUBLE PRECISION
);


CREATE TABLE DishIngredient (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        dish_id BIGINT,
        ingredient_id BIGINT,
        weight DOUBLE,
        FOREIGN KEY (dish_id) REFERENCES Dish(id),
        FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id)
);


