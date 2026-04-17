package org.kane.database.repository.recipe_recource.cooking_stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.recipe_recource.CookingStage;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.repository.recipe_recource.coefficient.CoefficientRepository;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-cooking-stage-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CookingStageRepositoryTest extends IntegrationTestBase {

        @Autowired
        private CookingStageRepository coefficientRepository;

        @Autowired
        private SavedEntities savedEntities;

        private Recipe savedRecipe;
        private List<CookingStage> savedCookingStages;
        private List<ImageModel> savedImageModels;
    @Autowired
    private CookingStageRepository cookingStageRepository;

        @BeforeEach
        void setUp() {
                savedRecipe = savedEntities.getRecipe();
                savedCookingStages = savedEntities.getCookingStages();
                savedImageModels = List.of(
                        ImageModel.builder()
                                .id(1L)
                                .url(Path.of("/images/avatars/default.jpg"))
                                .build(),
                        ImageModel.builder()
                                .id(2L)
                                .url(Path.of("/images/recipes/pasta.jpg"))
                                .build(),
                        ImageModel.builder()
                                .id(3L)
                                .url(Path.of("/images/recipes/salad.jpg"))
                                .build()
                );
                List.of(0,1,2).forEach(i -> {
                        savedCookingStages.get(i).addRecipe(savedRecipe);
                        savedCookingStages.get(i).setImage(savedImageModels.get(i));
                });
        }
        @Test
        void findAllShowDTOByRecipeID(){
                var cookingStages = cookingStageRepository.findAllShowDTOByRecipeID(savedRecipe.getId());
                assertThat(cookingStages).isNotEmpty();
                assertThat(cookingStages.size()).isEqualTo(3);
                List.of(0,1,2).forEach(i -> {
                    var cookingStage = cookingStages.get(i);
                    var savedCookingStage = savedCookingStages.get(i);
                    assertThat(cookingStage.getId()).isEqualTo(savedCookingStage.getId());
                    assertThat(cookingStage.getStageNumber()).isEqualTo(savedCookingStage.getStageNumber());
                    assertThat(cookingStage.getDescription()).isEqualTo(savedCookingStage.getDescription());
                    assertThat(cookingStage.getImageId()).isEqualTo(savedCookingStage.getImage().getId());
                });
        }

}