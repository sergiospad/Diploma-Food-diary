--liquibase formatted sql

-- Заполнение image_model
INSERT INTO image_model (id, url, image_type) VALUES
                                                  (1, '/images/avatars/default.jpg', 'USER'),
                                                  (2, '/images/recipes/pasta.jpg', 'RECIPE'),
                                                  (3, '/images/recipes/salad.jpg', 'RECIPE'),
                                                  (4, '/images/recipes/soup.jpg', 'RECIPE'),
                                                  (5, '/images/recipes/cake.jpg', 'RECIPE'),
                                                  (6, '/images/avatars/user1.jpg', 'USER'),
                                                  (7, '/images/recipes/steak.jpg', 'RECIPE'),
                                                  (8, '/images/recipes/porridge.jpg', 'RECIPE');

-- Заполнение users
INSERT INTO users (id, username, password, email, height_sm, birthdate, gender, role, created_at, avatar_id) VALUES
                                                                                                                 (1, 'john_doe', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'john@example.com', 175, '1990-05-15', 'M', 'USER', '2024-01-10 10:00:00', 1),
                                                                                                                 (2, 'jane_smith', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'jane@example.com', 165, '1992-08-20', 'FM', 'USER', '2024-01-11 11:30:00', 6),
                                                                                                                 (3, 'admin', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'admin@example.com', 180, '1988-03-10', 'M', 'ADMIN', '2024-01-09 09:00:00', 1),
                                                                                                                 (4, 'chef_mike', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'mike@example.com', 178, '1985-12-01', 'M', 'USER', '2024-01-12 14:20:00', 1),
                                                                                                                 (5, 'lisa_cook', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'lisa@example.com', 162, '1995-07-25', 'FM', 'ADMIN', '2024-01-13 16:45:00', 6);

-- Заполнение weight_record
INSERT INTO weight_record (id, measured_weight_kg, date_of_measurement, user_id) VALUES
                                                                                     (1, 85.50, '2024-01-01', 1),
                                                                                     (2, 84.20, '2024-01-08', 1),
                                                                                     (3, 83.00, '2024-02-15', 1),
                                                                                     (4, 85.30, '2024-01-01', 2),
                                                                                     (5, 64.80, '2024-01-07', 2),
                                                                                     (6, 64.10, '2024-01-14', 2),
                                                                                     (7, 90.00, '2024-01-01', 4),
                                                                                     (8, 89.00, '2024-01-10', 4),
                                                                                     (9, 88.00, '2024-01-20', 4);

-- Заполнение task
INSERT INTO task (id, beginning_date, ending_date, calories_deficit, target, status, target_weight_kg, user_id, start_weight_id) VALUES
                                                                                                                                     (1, '2024-01-01', NULL, 500.0, 'W_LOSS', 'ONGOING', 75.00, 1, 1),
                                                                                                                                     (2, '2024-01-01', NULL, 300.0, 'W_LOSS', 'ONGOING', 60.00, 2, 4),
                                                                                                                                     (3, '2024-01-15', NULL, 0.0, 'W_GAIN', 'ONGOING', 70.00, 4, 7),
                                                                                                                                     (4, '2023-10-01', '2023-12-31', 400.0, 'W_LOSS', 'COMPLETED', 72.00, 1, 1);


-- Заполнение recipe (наследуется от nutritional_info)
INSERT INTO nutritional_info (id, name, calories_per_100g, protein_per_100g, fat_per_100g, carbs_per_100g, private, discriminator, author_id) VALUES
                                                                                                                                                  (1, 'Куриная грудка', 165.0, 31.00, 3.60, 0.00, false, 'PRODUCT', 1),
                                                                                                                                                  (2, 'Рис белый', 130.0, 2.70, 0.30, 28.00, false, 'PRODUCT', 1),
                                                                                                                                                  (3, 'Брокколи', 34.0, 2.80, 0.40, 7.00, false, 'PRODUCT', 2),
                                                                                                                                                  (4, 'Куриный суп', 45.0, 4.50, 1.50, 4.00, false, 'RECIPE', 1),
                                                                                                                                                  (5, 'Салат Цезарь', 180.0, 8.00, 12.00, 10.00, false, 'RECIPE', 2),
                                                                                                                                                  (6, 'Овсяная каша', 68.0, 2.50, 1.50, 12.00, false, 'PRODUCT', 2),
                                                                                                                                                  (7, 'Греческий салат', 120.0, 3.00, 9.00, 6.00, false, 'RECIPE', 4),
                                                                                                                                                  (8, 'Стейк из говядины', 250.0, 26.00, 17.00, 0.00, false, 'PRODUCT', 4),
                                                                                                                                                  (9, 'Картофельное пюре', 110.0, 2.00, 4.00, 17.00, false, 'RECIPE', 1),
                                                                                                                                                  (10, 'Яблоко', 52.0, 0.30, 0.20, 14.00, false, 'PRODUCT', 2);

-- Заполнение recipe (наследуется от nutritional_info)
INSERT INTO recipe (id, summary, created_at, illustration_id, cooking_time) VALUES
                                                                                                                                                                                   (4,  'Наваристый куриный суп с лапшой и овощами', '2024-01-15 12:00:00', 3, 60),
                                                                                                                                                                                   (5,  'Классический салат Цезарь с курицей и сухариками', '2024-01-16 14:30:00', 2, 20),
                                                                                                                                                                                   (7,  'Свежий греческий салат с фетой и оливками', '2024-01-18 09:45:00', 3, 15),
                                                                                                                                                                                   (9,  'Нежное картофельное пюре со сливочным маслом', '2024-01-20 18:00:00', 8, 30);
-- Заполнение favourite_recipe
INSERT INTO favourite_recipe (id, user_id, recipe_id) VALUES
                                                          (1, 1, 4),
                                                          (2, 1, 5),
                                                          (3, 2, 5),
                                                          (4, 2, 7),
                                                          (5, 4, 4),
                                                          (6, 5, 7);

-- Заполнение tag
INSERT INTO tag (id, name, priority) VALUES
                                         (1, 'Супы', 2),
                                         (2, 'Салаты', 2),
                                         (3, 'Горячее', 2),
                                         (4, 'Завтрак', 2),
                                         (5, 'Диетическое', 1),
                                         (6, 'Быстрое', 1),
                                         (7, 'Праздничное', 2),
                                         (8, 'Итальянская', 2);

-- Заполнение tag_recipes
INSERT INTO tag_recipes (id, recipe_id, tag_id) VALUES
                                                    (1, 4, 1),
                                                    (2, 4, 5),
                                                    (3, 5, 2),
                                                    (4, 5, 6),
                                                    (5, 5, 7),
                                                    (6, 7, 2),
                                                    (7, 7, 5),
                                                    (8, 7, 8),
                                                    (9, 9, 3);

-- Заполнение cooking_stage
INSERT INTO cooking_stage (id, stage_number, description, recipe_id, image_id) VALUES
                                                                                   (1, 1, 'Нарежьте курицу и овощи', 4, NULL),
                                                                                   (2, 2, 'Залейте водой и варите 30 минут', 4, NULL),
                                                                                   (3, 3, 'Добавьте лапшу и варите ещё 10 минут', 4, NULL),
                                                                                   (4, 1, 'Обжарьте курицу и нарежьте салат', 5, NULL),
                                                                                   (5, 2, 'Смешайте все ингредиенты с соусом', 5, 2),
                                                                                   (6, 1, 'Нарежьте овощи и сыр кубиками', 7, NULL),
                                                                                   (7, 2, 'Заправьте оливковым маслом', 7, NULL),
                                                                                   (8, 1, 'Отварите картофель до готовности', 9, NULL),
                                                                                   (9, 2, 'Разомните с маслом и молоком', 9, NULL);

-- Заполнение category
INSERT INTO category (id, name) VALUES
                                    (1, 'Мясо'),
                                    (2, 'Крупы'),
                                    (3, 'Овощи'),
                                    (4, 'Фрукты'),
                                    (5, 'Молочные продукты'),
                                    (6, 'Напитки');

-- Заполнение product (наследуется от nutritional_info)
INSERT INTO product (id, description, category_id) VALUES
                                                         (1,  'Куриная грудка без кожи и костей', 1),
                                                         (2,  'Рис белый шлифованный', 2),
                                                         (3,  'Свежая брокколи', 3),
                                                         (6, 'Геркулес традиционный', 2),
                                                         (8,  'Говяжья вырезка', 1),
                                                         (10, 'Яблоки свежие', 4);

-- Заполнение measure_unit
INSERT INTO measure_unit (id, name) VALUES
                                        (1, 'г'),
                                        (2, 'кг'),
                                        (3, 'мл'),
                                        (4, 'л'),
                                        (5, 'шт'),
                                        (6, 'ст.л.'),
                                        (7, 'ч.л.');

-- Заполнение coefficient
INSERT INTO coefficient (id, conversion_factor, category_id, measure_unit_id) VALUES
                                                                                  (1, 100.0000, 1, 1),   -- 100г для мяса
                                                                                  (2, 100.0000, 2, 1),   -- 100г для круп
                                                                                  (3, 100.0000, 3, 1),   -- 100г для овощей
                                                                                  (4, 100.0000, 4, 1),   -- 100г для фруктов
                                                                                  (5, 150.0000, 2, 2),   -- 1кг крупы = 1000г
                                                                                  (6, 200.0000, 1, 2);   -- 1кг мяса = 1000г

-- Заполнение ingredient
INSERT INTO ingredient (id, weight_g, spec_measure_unit_id, recipe_id, product_id) VALUES
                                                                                       (1, 200.0, 1, 4, 1),   -- 200г курицы для супа
                                                                                       (2, 100.0, 1, 4, 2),   -- 100г риса для супа
                                                                                       (3, 100.0, 1, 4, 3),   -- 100г брокколи для супа
                                                                                       (4, 150.0, 1, 5, 1),   -- 150г курицы для Цезаря
                                                                                       (5, 50.0, 1, 5, 6),    -- 50г овсянки (неправильно, но для теста)
                                                                                       (6, 100.0, 1, 7, 10),  -- 100г яблок (не для салата, но для теста)
                                                                                       (7, 250.0, 1, 9, 10),  -- 250г яблок (не для пюре, но для теста)
                                                                                       (8, 150.0, 1, 9, 1);   -- 150г курицы (не для пюре)

-- Заполнение daily_diary_record
INSERT INTO daily_diary_record (id, record_date, user_id, auto_calculation) VALUES
                                                                                (1, '2024-01-15', 1, true),
                                                                                (2, '2024-01-15', 2, true),
                                                                                (3, '2024-01-16', 1, true),
                                                                                (4, '2024-01-16', 2, true),
                                                                                (5, '2024-01-17', 4, false);

-- Заполнение meal
INSERT INTO meal (id, mealtime, type, diary_record_id) VALUES
                                                           (1, '08:00:00', 'BREAKFAST', 1),
                                                           (2, '13:00:00', 'LUNCH', 1),
                                                           (3, '19:00:00', 'DINNER', 1),
                                                           (4, '08:30:00', 'BREAKFAST', 2),
                                                           (5, '12:30:00', 'LUNCH', 2),
                                                           (6, '09:00:00', 'BREAKFAST', 3),
                                                           (7, '14:00:00', 'LUNCH', 4);

-- Заполнение meal_item
INSERT INTO meal_item (id, weight_g, meal_id, nutrition_id) VALUES
                                                                (1, 200.0, 1, 6),   -- овсяная каша на завтрак
                                                                (2, 50.0, 1, 10),   -- яблоко на завтрак
                                                                (3, 250.0, 2, 4),   -- куриный суп на обед
                                                                (4, 150.0, 3, 5),   -- салат Цезарь на ужин
                                                                (5, 180.0, 4, 6),   -- овсяная каша на завтрак (user2)
                                                                (6, 100.0, 5, 10),  -- яблоко на обед (user2)
                                                                (7, 200.0, 6, 6),   -- овсяная каша на завтрак (user1, day2)
                                                                (8, 250.0, 7, 4);   -- куриный суп на обед (user2, day2)

-- Заполнение sports_activity
INSERT INTO sports_activity (id, name, burned_calories, diary_record_id) VALUES
                                                                             (1, 'Бег', 350.0, 1),
                                                                             (2, 'Ходьба', 150.0, 1),
                                                                             (3, 'Тренажерный зал', 400.0, 2),
                                                                             (4, 'Плавание', 300.0, 3),
                                                                             (5, 'Йога', 120.0, 4);

-- Заполнение comment
INSERT INTO comment (id, image_id, message, created_at, commentator_id, recipe_id) VALUES
                                                                                       (1, NULL, 'Отличный рецепт, очень вкусно!', '2024-01-20 10:00:00', 1, 4),
                                                                                       (2, NULL, 'Суп получился наваристым, спасибо!', '2024-01-21 15:30:00', 2, 4),
                                                                                       (3, 2, 'Салат супер, добавил свои сухарики', '2024-01-22 18:45:00', 4, 5),
                                                                                       (4, NULL, 'Быстро и вкусно, рекомендую', '2024-01-23 12:00:00', 5, 5),
                                                                                       (5, NULL, 'Салат освежает, хорошо для лета', '2024-01-24 09:15:00', 1, 7);
