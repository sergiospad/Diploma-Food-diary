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
SELECT setval('image_model_id_seq', (SELECT COALESCE(MAX(id), 1) FROM image_model), true);

-- Заполнение users
INSERT INTO users (id, username, password, email, height_sm, birthdate, gender, role, created_at, avatar_id) VALUES
                                                                                                                 (1, 'john_doe', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'john@example.com', 175, '1990-05-15', 'M', 'USER', '2024-01-10 10:00:00', 1),
                                                                                                                 (2, 'jane_smith', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'jane@example.com', 165, '1992-08-20', 'FM', 'USER', '2024-01-11 11:30:00', 6),
                                                                                                                 (3, 'admin', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'admin@example.com', 180, '1988-03-10', 'M', 'ADMIN', '2024-01-09 09:00:00', 1),
                                                                                                                 (4, 'chef_mike', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'mike@example.com', 178, '1985-12-01', 'M', 'USER', '2024-01-12 14:20:00', 1),
                                                                                                                 (5, 'lisa_cook', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'lisa@example.com', 162, '1995-07-25', 'FM', 'ADMIN', '2024-01-13 16:45:00', 6);
SELECT setval('users_id_seq', (SELECT COALESCE(MAX(id), 1) FROM users), true);

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
SELECT setval('nutritional_info_id_seq', (SELECT COALESCE(MAX(id), 1) FROM nutritional_info), true);



-- Заполнение recipe (наследуется от nutritional_info)
INSERT INTO recipe (id, summary, created_at, illustration_id, cooking_time) VALUES
                                                                                                                                                                                            (4, 'Наваристый куриный суп с лапшой и овощами', '2024-01-15 12:00:00', 3, 60),
                                                                                                                                                                                            (5, 'Классический салат Цезарь с курицей и сухариками', '2024-01-16 14:30:00', 2, 20),
                                                                                                                                                                                            (7,  'Свежий греческий салат с фетой и оливками', '2024-01-18 09:45:00', 3, 15),
                                                                                                                                                                                            (9,  'Нежное картофельное пюре со сливочным маслом', '2024-01-20 18:00:00', 8, 30);
SELECT setval('recipe_id_seq', (SELECT COALESCE(MAX(id), 1) FROM recipe), true);

-- Заполнение comment
INSERT INTO comment (id, image_id, message, created_at, commentator_id, recipe_id) VALUES
                                                                                       (1, 3, 'Отличный рецепт, очень вкусно!', '2024-01-20 10:00:00', 1, 4),
                                                                                       (2, NULL, 'Суп получился наваристым, спасибо!', '2024-01-21 15:30:00', 2, 4),
                                                                                       (3, 2, 'Салат супер, добавил свои сухарики', '2024-01-22 18:45:00', 4, 5),
                                                                                       (4, NULL, 'Быстро и вкусно, рекомендую', '2024-01-23 12:00:00', 5, 5),
                                                                                       (5, NULL, 'Салат освежает, хорошо для лета', '2024-01-24 09:15:00', 1, 7);
SELECT setval('comment_id_seq', (SELECT COALESCE(MAX(id), 1) FROM comment), true);


