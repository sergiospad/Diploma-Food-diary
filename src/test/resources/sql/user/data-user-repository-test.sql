-- Minimal dataset for UserRepositoryTest

INSERT INTO image_model (id, url, image_type) VALUES
    (1, '/images/avatars/default.jpg', 'USER'),
    (6, '/images/avatars/user1.jpg', 'USER');

INSERT INTO users (id, username, password, email, height_sm, birthdate, gender, role, created_at, avatar_id) VALUES
    (1, 'john_doe', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'john@example.com', 175, '1990-05-15', 'M', 'USER', '2024-01-10 10:00:00', 1),
    (2, 'jane_smith', '$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy', 'jane@example.com', 165, '1992-08-20', 'FM', 'USER', '2024-01-11 11:30:00', 6);

INSERT INTO weight_record (id, measured_weight_kg, date_of_measurement, user_id) VALUES
    (1, 85.50, '2024-01-01', 1),
    (2, 84.20, '2024-01-08', 1),
    (3, 83.00, '2024-01-15', 1);
