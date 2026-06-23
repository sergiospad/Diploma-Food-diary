--liquibase formatted sql

--changeset kane:1
CREATE INDEX idx_tag_name ON tag(name);

--changeset kane:2
CREATE INDEX idx_recipe_author ON nutritional_info(author_id);

--changeset kane:3
CREATE INDEX idx_recipe_title ON nutritional_info(name);

--changeset kane:4
CREATE INDEX idx_ingredient_recipe ON ingredient(recipe_id);

--changeset kane:5
CREATE INDEX idx_ingredient_product ON ingredient(product_id);

--changeset kane:6
CREATE INDEX idx_diary_user_date ON daily_diary_record(user_id, record_date);

--changeset kane:7
CREATE INDEX idx_meal_diary ON meal(diary_record_id);

--changeset kane:8
CREATE INDEX idx_weight_user_date ON weight_record(user_id, date_of_measurement);
