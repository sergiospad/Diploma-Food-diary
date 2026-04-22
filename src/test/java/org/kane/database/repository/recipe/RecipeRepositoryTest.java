package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.exceptions.not_found.RecipeNotFoundException;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.kane.database.entity.QRecipe.recipe;


@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe/data-recipe-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
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
    @Autowired
    private UserRepository userRepository;

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

    @Test
    void findAllPreviewDTO(){
        BooleanBuilder predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByNew(predicate, pageable);

        assertNotNull(result);
        assertEquals(4L, result.getTotalElements());
        assertEquals(4, result.getContent().size());
        assertNotNull(result.getContent().getFirst().getId());
        assertNotNull(result.getContent().getFirst().getName());
        assertNotNull(result.getContent().getFirst().getSummary());
        assertEquals(pageable, result.getPageable());
    }

    @Test
    void findAllPreviewDTO1(){
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(recipe.isPrivate.isFalse());
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByNew(predicate, pageable);
        assertNotNull(result);
        assertEquals(3L, result.getTotalElements());
    }

    @Test
    void findAllPreviewDTO2(){
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(recipe.isPrivate.isFalse());
        predicate.and(recipe.tags.any().id.in(1L, 5L, 3L));
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByNew(predicate, pageable);
        assertNotNull(result);
        assertEquals(2L, result.getTotalElements());
    }

    @Test
    void findAllPreviewDTO3(){
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(recipe.author.id.eq(1L));
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByNew(predicate, pageable);
        assertNotNull(result);
        assertEquals(2L, result.getTotalElements());
    }

    @Test
    void findAllPreviewDTO6(){
        BooleanBuilder predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByOlder(predicate, pageable);

        assertNotNull(result);
        assertEquals(4L, result.getTotalElements());
        assertEquals(4, result.getContent().size());
        assertNotNull(result.getContent().getFirst().getId());
        assertNotNull(result.getContent().getFirst().getName());
        assertNotNull(result.getContent().getFirst().getSummary());
        assertEquals(pageable, result.getPageable());
    }

    @Test
    void findAllPreviewDTO4(){
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(recipe.author.id.eq(1L));
        predicate.and(recipe.isPrivate.isFalse());
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByNew(predicate, pageable);
        assertNotNull(result);
        assertEquals(1L, result.getTotalElements());
    }

    @Test
    void findAllPreviewDTO5(){
        var user = userRepository.findById(1L).orElseThrow();
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(recipe.in(user.getFavouriteRecipes()));
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByNew(predicate, pageable);
        assertNotNull(result);
        assertThat(result.getTotalElements()).isEqualTo(2L);
    }

    @Test
    void findAllPreviewDTO7(){
        BooleanBuilder predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        Page<RecipePreviewDTO> result = recipeRepository.findAllPreviewDTOOrderedByPopular(predicate, pageable);

        assertNotNull(result);
        assertEquals(4L, result.getTotalElements());
        assertEquals(4, result.getContent().size());
        assertNotNull(result.getContent().getFirst().getId());
        assertNotNull(result.getContent().getFirst().getName());
        assertNotNull(result.getContent().getFirst().getSummary());
        assertEquals(pageable, result.getPageable());
    }
}