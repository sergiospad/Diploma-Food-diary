package org.kane.database.repository.recipe;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreShowProjection;
import org.kane.exceptions.not_found.RecipeNotFoundException;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.kane.database.entity.QRecipe.recipe;
import static org.kane.database.entity.QUser.user;

@Sql({"classpath:sql/data-recipe-repository-test.sql"})
class RecipeRepositoryTest extends IntegrationTestBase{
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private EntityManager em;
    private Recipe savedRecipe;
    private User savedUser;
    private ImageModel savedIllustrationImage;
    private ImageModel userAvatar;

    @BeforeEach
    void beforeEach(){
        savedRecipe = savedEntities.getRecipe();
        savedUser = savedEntities.getUser();
        savedRecipe.setAuthor(savedUser);
        savedIllustrationImage = savedEntities.getIllustration();
        savedRecipe.setIllustration(savedIllustrationImage);
        userAvatar = savedEntities.getAvatar();
        savedUser.setAvatar(userAvatar);
    }
    @Test
    void findById() {
        var recipe = recipeRepository.findById(4L)
                .orElseThrow(()->new RecipeNotFoundException("Recipe not found"));
        System.out.println(recipe.getCalories());
        assertThat(recipe).isNotNull();
        assertThat(recipe.getId()).isEqualTo(savedRecipe.getId());
        assertThat(recipe.getName()).isEqualTo(savedRecipe.getName());
        assertThat(recipe.getCalories()).isEqualTo(savedRecipe.getCalories());
        assertThat(recipe.getProtein()).isEqualTo(savedRecipe.getProtein());
        assertThat(recipe.getFat()).isEqualTo(savedRecipe.getFat());
        assertThat(recipe.getCarbs()).isEqualTo(savedRecipe.getCarbs());
        assertThat(recipe.getIsPrivate()).isEqualTo(savedRecipe.getIsPrivate());
        assertThat(recipe.getAuthor().getId()).isEqualTo(savedUser.getId());
        assertThat(recipe.getSummary()).isEqualTo(savedRecipe.getSummary());
        assertThat(recipe.getCreatedAt()).isEqualTo(savedRecipe.getCreatedAt());
        assertThat(recipe.getIllustration().getId()).isEqualTo(savedIllustrationImage.getId());
        assertThat(recipe.getCookingTime()).isEqualTo(savedRecipe.getCookingTime());
    }

//    @Test
//    void findAllPreviewDTO() {
//    }
//
//    @Test
//    void findSummaryDTOByItem() {
//    }
//
//    @Test
//    void findTitleDTOByItem() {
//    }

    @Test
    void getRecipePreShowProjByID() {
        var proj = recipeRepository.getRecipePreShowProjByID(4L);

        assertThat(proj).isNotNull();
        assertThat(proj.getId()).isEqualTo(savedRecipe.getId());
        assertThat(proj.getAuthorName()).isEqualTo(savedUser.getUsername());
        assertThat(proj.getAvatarID()).isEqualTo(savedUser.getAvatar().getId());
        assertThat(proj.getCookingTime()).isEqualTo(savedRecipe.getCookingTime());
        assertThat(proj.getName()).isEqualTo(savedRecipe.getName());
        assertThat(proj.getSummary()).isEqualTo(savedRecipe.getSummary());
        assertThat(proj.getIllustrationID()).isEqualTo(savedIllustrationImage.getId());
    }
}