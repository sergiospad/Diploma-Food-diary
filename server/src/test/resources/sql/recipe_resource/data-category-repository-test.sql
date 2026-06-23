-- Заполнение category
INSERT INTO category (id, name) VALUES
                                    (1, 'Мясо'),
                                    (2, 'Крупы'),
                                    (3, 'Овощи'),
                                    (4, 'Фрукты'),
                                    (5, 'Молочные продукты'),
                                    (6, 'Напитки');
SELECT setval('category_id_seq', (SELECT COALESCE(MAX(id), 1) FROM category), true);

-- Заполнение measure_unit
INSERT INTO measure_unit (id, name) VALUES
                                        (1, 'г'),
                                        (2, 'кг'),
                                        (3, 'мл'),
                                        (4, 'л'),
                                        (5, 'шт'),
                                        (6, 'ст.л.'),
                                        (7, 'ч.л.');
SELECT setval('measure_unit_id_seq', (SELECT COALESCE(MAX(id), 1) FROM measure_unit), true);

-- Заполнение coefficient
INSERT INTO coefficient (id, conversion_factor, category_id, measure_unit_id) VALUES
                                                                                  (1, 100.0000, 1, 1),   -- 100г для мяса
                                                                                  (2, 100.0000, 2, 1),   -- 100г для круп
                                                                                  (3, 100.0000, 3, 1),   -- 100г для овощей
                                                                                  (4, 100.0000, 4, 1),   -- 100г для фруктов
                                                                                  (5, 150.0000, 2, 2),   -- 1кг крупы = 1000г
                                                                                  (6, 200.0000, 1, 2);   -- 1кг мяса = 1000г
SELECT setval('coefficient_id_seq', (SELECT COALESCE(MAX(id), 1) FROM coefficient), true);

