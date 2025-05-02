-- Insert categories into IngredientCategory table
INSERT INTO IngredientCategory (name) VALUES ('VEGETABLE');
INSERT INTO IngredientCategory (name) VALUES ('SPICE');
INSERT INTO IngredientCategory (name) VALUES ('MEAT');
INSERT INTO IngredientCategory (name) VALUES ('DAIRY');

-- Insert ingredients into Ingredient table
INSERT INTO Ingredient (name, calories_per_100g, category)
VALUES
    ('Tomato', 19, 'VEGETABLE'),
    ('Cherry', 23, 'VEGETABLE'),
    ('Cucumber', 15, 'VEGETABLE'),
    ('Sweet Pepper', 27, 'VEGETABLE'),
    ('Iceberg Lettuce', 14, 'VEGETABLE'),
    ('Salt', 10, 'SPICE'),
    ('Olive Oil', 884, 'SPICE'),
    ('Black Pepper', 210, 'SPICE'),
    ('Chicken Fillet', 106, 'MEAT'),
    ('Beef', 250, 'MEAT'),
    ('Feta', 264, 'DAIRY'),
    ('Parmesan', 431, 'DAIRY');


INSERT INTO Dish (name, type, description, total_calories, total_weight)
VALUES
    ('Greek Salad', 'SALAD', 'A refreshing mix of tomatoes, cucumbers, olives, feta cheese, and olive oil.', 291.6, 220),
    ('Chicken Soup', 'SOUP', 'A warm, hearty soup with chicken fillet, spices, and vegetables.', 165.8, 158),
    ('Spaghetti Carbonara', 'MAIN', 'Pasta with a creamy sauce made from eggs, cheese, pancetta, and pepper.', 600, 350),
    ('Minestrone Soup', 'SOUP', 'A rich vegetable soup with beans, pasta, and tomatoes.', 180, 400),
    ('Caesar Salad', 'SALAD', 'A classic salad with romaine lettuce, croutons, parmesan, and Caesar dressing.', 240, 220),
    ('Margherita Pizza', 'MAIN', 'A simple and delicious pizza topped with fresh tomatoes, mozzarella, and basil.', 800, 450),
    ('Chocolate Lava Cake', 'DESSERT', 'A warm, molten-centered chocolate cake served with vanilla ice cream.', 450, 150);


INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM Dish d
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN (VALUES
                   ('Tomato', 100),
                   ('Cucumber', 50),
                   ('Salt', 5),
                   ('Olive Oil', 15),
                   ('Feta', 50)
) AS w(ingredient_name, weight)
WHERE d.name = 'Greek Salad' AND i.name = w.ingredient_name;

-- Chicken Soup ingredients
INSERT INTO DishIngredient (dish_id, ingredient_id, weight)
SELECT d.id, i.id, w.weight
FROM Dish d
         JOIN Ingredient i ON i.name = w.ingredient_name
         JOIN (VALUES
                   ('Chicken Fillet', 150),
                   ('Salt', 5),
                   ('Black Pepper', 3)
) AS w(ingredient_name, weight)
WHERE d.name = 'Chicken Soup' AND i.name = w.ingredient_name;