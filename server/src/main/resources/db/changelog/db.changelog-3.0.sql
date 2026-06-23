--liquibase formatted sql

--changeset kane:1
ALTER TABLE daily_diary_record
    ADD CONSTRAINT daily_diary_record_pk
        UNIQUE (user_id, record_date);

--changeset kane:2
ALTER TABLE meal
    ADD CONSTRAINT type_diary_record_id
        UNIQUE (type, diary_record_id);

--changeset kane:3
ALTER TABLE meal_item
    ADD CONSTRAINT meal_item_nutr_id_meal_id
        UNIQUE(nutrition_id, meal_id);
