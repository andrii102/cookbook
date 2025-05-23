-- Insert ingredient categories
MERGE INTO IngredientCategory (name) KEY(name) VALUES
    ('VEGETABLE'),
    ('SPICE'),
    ('MEAT'),
    ('DAIRY'),
    ('GRAIN'),
    ('OTHER');

-- Insert base ingredients
MERGE INTO Ingredient (name, calories_per_100g, category) KEY(name) VALUES
    ('Tomato', 18, 'VEGETABLE'),
    ('Cherry', 23, 'VEGETABLE'),
    ('Cucumber', 16, 'VEGETABLE'),
    ('Sweet Pepper', 27, 'VEGETABLE'),
    ('Iceberg Lettuce', 14, 'VEGETABLE'),
    ('Red Onion', 40, 'VEGETABLE'),
    ('Garlic', 149, 'VEGETABLE'),
    ('Carrot', 41, 'VEGETABLE'),
    ('Celery', 14, 'VEGETABLE'),

    ('Salt', 10, 'SPICE'),
    ('Olive Oil', 898, 'SPICE'),
    ('Black Pepper', 210, 'SPICE'),
    ('Basil', 23, 'SPICE'),
    ('Oregano', 265, 'SPICE'),
    ('Thyme', 101, 'SPICE'),
    ('Paprika', 282, 'SPICE'),

    ('Chicken Fillet', 106, 'MEAT'),
    ('Beef', 251, 'MEAT'),
    ('Bacon', 541, 'MEAT'),
    ('Pancetta', 458, 'MEAT'),
    ('Salmon', 208, 'MEAT'),
    ('Shrimp', 99, 'MEAT'),

    ('Feta', 264, 'DAIRY'),
    ('Parmesan', 431, 'DAIRY'),
    ('Mozzarella', 280, 'DAIRY'),
    ('Egg', 143, 'DAIRY'),
    ('Butter', 717, 'DAIRY'),
    ('Heavy Cream', 340, 'DAIRY'),

    ('Spaghetti', 158, 'GRAIN'),
    ('Penne', 158, 'GRAIN'),
    ('Breadcrumbs', 395, 'GRAIN'),

    ('Sugar', 387, 'OTHER'),
    ('Flour', 364, 'OTHER'),
    ('Dark Chocolate', 598, 'OTHER'),
    ('Honey', 304, 'OTHER');

-- Insert dishes
MERGE INTO Dish (name, type, description, total_calories, total_weight) KEY(name) VALUES
    ('Greek Salad', 'SALAD', 'Classic Mediterranean salad', 291, 220),
    ('Chicken Soup', 'SOUP', 'Homestyle chicken soup', 165, 158),
    ('Spaghetti Carbonara', 'MAIN', 'Creamy Italian pasta', 600, 350),
    ('Minestrone Soup', 'SOUP', 'Hearty vegetable soup', 180, 400),
    ('Caesar Salad', 'SALAD', 'Classic Caesar with croutons', 240, 220),
    ('Margherita Pizza', 'MAIN', 'Traditional Neapolitan pizza', 800, 450),
    ('Chocolate Lava Cake', 'DESSERT', 'Decadent molten cake', 450, 150),
    ('Caprese Salad', 'SALAD', 'Fresh mozzarella and tomatoes', 320, 250),
    ('Garlic Shrimp Pasta', 'MAIN', 'Garlicky shrimp with pasta', 450, 300);

-- Greek Salad
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Tomato', 100),
          ('Cucumber', 50),
          ('Red Onion', 30),
          ('Feta', 50),
          ('Olive Oil', 15),
          ('Salt', 2),
          ('Black Pepper', 1)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Greek Salad'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Chicken Soup
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Chicken Fillet', 150),
          ('Carrot', 50),
          ('Celery', 30),
          ('Salt', 3),
          ('Black Pepper', 2)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Chicken Soup'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Spaghetti Carbonara
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Spaghetti', 200),
          ('Pancetta', 100),
          ('Egg', 100),
          ('Parmesan', 50),
          ('Black Pepper', 3)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Spaghetti Carbonara'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Minestrone Soup
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Tomato', 100),
          ('Carrot', 50),
          ('Celery', 30),
          ('Penne', 50),
          ('Olive Oil', 10),
          ('Salt', 3)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Minestrone Soup'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Caesar Salad
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Iceberg Lettuce', 150),
          ('Parmesan', 30),
          ('Breadcrumbs', 20),
          ('Olive Oil', 20),
          ('Salt', 2)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Caesar Salad'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Margherita Pizza
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Tomato', 150),
          ('Mozzarella', 200),
          ('Basil', 10),
          ('Olive Oil', 10),
          ('Salt', 2)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Margherita Pizza'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Chocolate Lava Cake
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Dark Chocolate', 100),
          ('Butter', 50),
          ('Egg', 100),
          ('Sugar', 50),
          ('Flour', 30)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Chocolate Lava Cake'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Caprese Salad
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Tomato', 120),
          ('Mozzarella', 120),
          ('Basil', 5),
          ('Olive Oil', 10)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Caprese Salad'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);

-- Garlic Shrimp Pasta
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM (VALUES
          ('Shrimp', 150),
          ('Spaghetti', 200),
          ('Garlic', 20),
          ('Olive Oil', 30),
          ('Salt', 3)
     ) AS w(ingredient_name, weight)
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN Dish d ON d.name = 'Garlic Shrimp Pasta'
WHERE NOT EXISTS (
    SELECT 1 FROM DishIngredient di
    WHERE di.dish_id = d.id AND di.ingredient_id = i.id
);
