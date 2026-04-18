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

INSERT INTO users (id, username, password, email, height_sm, birthdate, gender, role, created_at, avatar_id) VALUES
                                                                                                                 (1, 'john_doe', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'john@example.com', 175, '1990-05-15', 'M', 'USER', '2024-01-10 10:00:00', 1),
                                                                                                                 (2, 'jane_smith', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'jane@example.com', 165, '1992-08-20', 'FM', 'USER', '2024-01-11 11:30:00', 6),
                                                                                                                 (3, 'admin', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'admin@example.com', 180, '1988-03-10', 'M', 'ADMIN', '2024-01-09 09:00:00', 1),
                                                                                                                 (4, 'chef_mike', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'mike@example.com', 178, '1985-12-01', 'M', 'USER', '2024-01-12 14:20:00', 1),
                                                                                                                 (5, 'lisa_cook', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'lisa@example.com', 162, '1995-07-25', 'FM', 'ADMIN', '2024-01-13 16:45:00', 6);

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

