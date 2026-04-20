-- Заполнение category
INSERT INTO category (id, name) VALUES
                                    (1, 'Мясо'),
                                    (2, 'Крупы'),
                                    (3, 'Овощи'),
                                    (4, 'Фрукты'),
                                    (5, 'Молочные продукты'),
                                    (6, 'Напитки');
SELECT setval('category_id_seq', (SELECT COALESCE(MAX(id), 1) FROM category), true);
