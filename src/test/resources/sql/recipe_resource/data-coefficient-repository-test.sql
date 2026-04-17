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
-- Заполнение category
INSERT INTO category (id, name) VALUES
                                    (1, 'Мясо'),
                                    (2, 'Крупы'),
                                    (3, 'Овощи'),
                                    (4, 'Фрукты'),
                                    (5, 'Молочные продукты'),
                                    (6, 'Напитки');

-- Заполнение product (наследуется от nutritional_info)
INSERT INTO product (id, name, calories_per_100g, protein_per_100g, fat_per_100g, carbs_per_100g, private, discriminator, author_id, description, category_id) VALUES
                                                                                                                                                                   (1, 'Куриная грудка', 165.0, 31.00, 3.60, 0.00, false, 'PRODUCT', 1, 'Куриная грудка без кожи и костей', 1),
                                                                                                                                                                   (2, 'Рис белый', 130.0, 2.70, 0.30, 28.00, false, 'PRODUCT', 1, 'Рис белый шлифованный', 2),
                                                                                                                                                                   (3, 'Брокколи', 34.0, 2.80, 0.40, 7.00, false, 'PRODUCT', 2, 'Свежая брокколи', 3),
                                                                                                                                                                   (6, 'Овсяная каша', 68.0, 2.50, 1.50, 12.00, false, 'PRODUCT', 2, 'Геркулес традиционный', 2),
                                                                                                                                                                   (8, 'Стейк из говядины', 250.0, 26.00, 17.00, 0.00, false, 'PRODUCT', 4, 'Говяжья вырезка', 1),
                                                                                                                                                                   (10, 'Яблоко', 52.0, 0.30, 0.20, 14.00, false, 'PRODUCT', 2, 'Яблоки свежие', 4);

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



