--liquibase formatted sql

--changeset kane:1
CREATE TABLE IF NOT EXISTS image_model(
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    image_type VARCHAR(15) NOT NULL
);

--changeset kane:2
CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(60) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(30) NOT NULL ,
    height_sm SMALLINT,
    birthdate DATE,
    gender VARCHAR(3),
    role VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    avatar_ID BIGINT REFERENCES image_model
);

--changeset kane:3
CREATE TABLE IF NOT EXISTS weight_record(
    id BIGSERIAL PRIMARY KEY,
    measured_weight_kg NUMERIC(5, 2) NOT NULL,
    date_of_measurement DATE NOT NULL,
    user_id BIGINT REFERENCES users ON DELETE CASCADE NOT NULL
);

--changeset kane:4
CREATE TABLE IF NOT EXISTS task(
    id BIGSERIAL PRIMARY KEY,
    beginning_date DATE NOT NULL,
    ending_date DATE,
    calories_deficit NUMERIC(6,2) NOT NULL,
    target VARCHAR(10) NOT NULL ,
    status VARCHAR(10) NOT NULL ,
    target_weight_kg NUMERIC(5,2) NOT NULL ,
    user_ID BIGINT REFERENCES users ON DELETE CASCADE NOT NULL,
    start_weight_ID BIGINT REFERENCES weight_record NOT NULL
);



--changeset kane:5
CREATE TABLE IF NOT EXISTS nutritional_info(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE,
    calories_per_100g NUMERIC(6,2) NOT NULL ,
    protein_per_100g NUMERIC(8, 4) NOT NULL,
    fat_per_100g NUMERIC(8, 4) NOT NULL,
    carbs_per_100g NUMERIC(8, 4) NOT NULL,
    private BOOLEAN DEFAULT FALSE,
    discriminator VARCHAR(10) NOT NULL,
    author_ID BIGINT REFERENCES users NOT NULL
);

--changeset kane:6
CREATE TABLE IF NOT EXISTS recipe(
    id BIGSERIAL PRIMARY KEY,
    summary TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    illustration_ID BIGINT REFERENCES image_model,
    FOREIGN KEY (id) references nutritional_info(id),
    cooking_time SMALLINT
);

--changeset kane:7
CREATE TABLE IF NOT EXISTS favourite_recipe(
    id BIGSERIAL PRIMARY KEY,
    user_ID BIGINT REFERENCES users ON DELETE CASCADE NOT NULL ,
    recipe_ID BIGINT REFERENCES recipe ON DELETE CASCADE NOT NULL,
    UNIQUE (user_ID, recipe_ID)
);

--changeset kane:8
CREATE TABLE IF NOT EXISTS tag(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL ,
    priority SMALLINT DEFAULT 2
);

--changeset kane:9
CREATE TABLE IF NOT EXISTS  tag_recipes(
    id BIGSERIAL PRIMARY KEY,
    recipe_ID BIGINT REFERENCES recipe ON DELETE CASCADE NOT NULL ,
    tag_ID BIGINT REFERENCES tag ON DELETE CASCADE NOT NULL ,
    UNIQUE (recipe_ID, tag_ID)
);

--changeset kane:10
CREATE TABLE IF NOT EXISTS cooking_stage(
    id BIGSERIAL PRIMARY KEY,
    stage_number SMALLINT NOT NULL ,
    description TEXT NOT NULL,
    recipe_ID BIGINT REFERENCES recipe ON DELETE CASCADE NOT NULL,
    image_ID BIGINT REFERENCES image_model
);

--changeset kane:11
CREATE TABLE IF NOT EXISTS category(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

--changeset kane:12
CREATE TABLE IF NOT EXISTS product(
    id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL ,
    category_ID BIGINT REFERENCES category NOT NULL,
    FOREIGN KEY (id) REFERENCES nutritional_info(id)
);

--changeset kane:13
CREATE TABLE IF NOT EXISTS measure_unit(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL
);

--changeset kane:14
CREATE TABLE IF NOT EXISTS coefficient(
    id BIGSERIAL PRIMARY KEY,
    conversion_factor NUMERIC(8, 4) NOT NULL ,
    category_ID BIGINT REFERENCES category NOT NULL ,
    measure_unit_ID BIGINT REFERENCES measure_unit ON DELETE CASCADE NOT NULL,
    UNIQUE (category_ID, measure_unit_ID)
);

--changeset kane:15
CREATE TABLE IF NOT EXISTS ingredient(
    id BIGSERIAL PRIMARY KEY,
    weight_g NUMERIC(5,1) NOT NULL ,
    spec_measure_unit_ID BIGINT REFERENCES measure_unit NOT NULL ,
    recipe_ID BIGINT REFERENCES recipe ON DELETE CASCADE NOT NULL ,
    product_ID BIGINT REFERENCES product NOT NULL
);

--changeset kane:16
CREATE TABLE IF NOT EXISTS daily_diary_record(
    id BIGSERIAL PRIMARY KEY,
    record_date DATE DEFAULT now(),
    user_ID BIGINT REFERENCES users ON DELETE CASCADE NOT NULL,
    auto_calculation BOOLEAN DEFAULT FALSE
);

--changeset kane:17
CREATE TABLE IF NOT EXISTS meal(
    id BIGSERIAL PRIMARY KEY,
    mealtime TIME NOT NULL ,
    type VARCHAR(30) NOT NULL,
    diary_record_ID BIGINT REFERENCES daily_diary_record ON DELETE CASCADE NOT NULL,
    UNIQUE (mealtime, diary_record_id)
);

--changeset kane:18
CREATE TABLE IF NOT EXISTS meal_item(
    id BIGSERIAL PRIMARY KEY,
    weight_g NUMERIC(5,1) NOT NULL,
    meal_ID BIGINT REFERENCES meal ON DELETE CASCADE NOT NULL ,
    nutrition_ID BIGINT REFERENCES nutritional_info NOT NULL
);

--changeset kane:19
CREATE TABLE IF NOT EXISTS sports_activity(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL ,
    burned_calories NUMERIC(6,2) NOT NULL ,
    diary_record_ID BIGINT REFERENCES daily_diary_record ON DELETE CASCADE NOT NULL
);

--changeset kane:20
CREATE TABLE IF NOT EXISTS comment(
    id BIGSERIAL PRIMARY KEY,
    image_ID BIGINT REFERENCES image_model,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    commentator_ID BIGINT REFERENCES users ON DELETE CASCADE NOT NULL ,
    recipe_ID BIGINT REFERENCES recipe ON DELETE CASCADE
);


