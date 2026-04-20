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

-- Заполнение weight_record
INSERT INTO weight_record (id, measured_weight_kg, date_of_measurement, user_id) VALUES
                                                                                     (1, 85.50, '2024-01-01', 1),
                                                                                     (2, 84.20, '2024-01-08', 1),
                                                                                     (3, 83.00, '2024-01-15', 1),
                                                                                     (4, 65.30, '2024-01-01', 2),
                                                                                     (5, 64.80, '2024-01-07', 2),
                                                                                     (6, 64.10, '2024-01-14', 2),
                                                                                     (7, 90.00, '2024-01-01', 4),
                                                                                     (8, 89.00, '2024-01-10', 4),
                                                                                     (9, 88.00, '2024-01-20', 4);
SELECT setval('weight_record_id_seq', (SELECT COALESCE(MAX(id), 1) FROM weight_record), true);

-- Заполнение task
INSERT INTO task (id, beginning_date, ending_date, calories_deficit, target, status, target_weight_kg, user_id, start_weight_id) VALUES
                                                                                                                                     (1, '2024-01-01', NULL, 500.0, 'W_LOSS', 'ONGOING', 75.00, 1, 1),
                                                                                                                                     (2, '2024-01-01', NULL, 300.0, 'W_LOSS', 'ONGOING', 60.00, 2, 4),
                                                                                                                                     (3, '2024-01-15', NULL, 0.0, 'W_GAIN', 'ONGOING', 70.00, 4, 7),
                                                                                                                                     (4, '2023-10-01', '2023-12-31', 400.0, 'W_LOSS', 'COMPLETED', 72.00, 1, 1);
SELECT setval('task_id_seq', (SELECT COALESCE(MAX(id), 1) FROM task), true);


