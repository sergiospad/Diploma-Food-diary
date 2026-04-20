package org.kane.domain.service.recipe_recource.cooking_stage;

import org.junit.jupiter.api.Test;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.recipe_recource.cooking_stage.CookingStageRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageShowDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

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
class CookingStageServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CookingStageRepository cookingStageRepository;
    @Autowired
    private CookingStageService cookingStageService;

    @Test
    void createCookingStage() {
        var recipe = recipeRepository.findById(4L).orElseThrow();
        var cscdto = CookingStageCreateDTO.builder()
                .description("testDescripton")
                .stageNumber((short)4)
                .imageID(1L)
                .build();
        var stage = cookingStageService.createCookingStage(cscdto, recipe);
        assertThat(stage).isNotNull();
        assertThat(stage.getId()).isEqualTo(10L);
        assertThat(stage.getDescription()).isEqualTo(cscdto.getDescription());
        assertThat(stage.getRecipe().getId()).isEqualTo(recipe.getId());
        assertThat(stage.getImage().getId()).isEqualTo(cscdto.getImageID());

    }

    @Test
    void editCookingStage() {
        var csedto =new CookingStageEditDescDTO(1L, "TestDescription");
        var cs = cookingStageRepository.findById(csedto.getId()).orElseThrow();
        assertThat(cs).isNotNull();
        assertThat(cs.getDescription()).isNotEqualTo(csedto.getDescription());
        cookingStageService.editCookingStage(csedto);
        cs = cookingStageRepository.findById(csedto.getId()).orElseThrow();
        assertThat(cs).isNotNull();
        assertThat(cs.getDescription()).isEqualTo( csedto.getDescription());
    }

    @Test
    void getAllShowDTOByRecipeID() {
        var stages = cookingStageService.getAllShowDTOByRecipeID(4L);
        assertThat(stages).isNotEmpty().hasSize(3);
        assertThat(stages.stream().map(CookingStageShowDTO::getStageNumber))
                .containsExactly((short)1, (short)2, (short)3);
        assertThat(stages.stream().map(CookingStageShowDTO::getDescription).toList())
                .containsExactly("Нарежьте курицу и овощи", "Залейте водой и варите 30 минут", "Добавьте лапшу и варите ещё 10 минут");
        assertThat(stages.stream().map(CookingStageShowDTO::getImageId)).containsExactly(1L, 2L, 3L);
    }

    @Test
    void removeCookingStage() {
        var stages = cookingStageService.getAllShowDTOByRecipeID(4L);
        assertThat(stages).isNotNull().hasSize(3);
        assertThat(stages.stream().map(CookingStageShowDTO::getStageNumber))
                .containsExactly((short)1, (short)2, (short)3);
        cookingStageService.removeCookingStage(2L);
        stages = cookingStageService.getAllShowDTOByRecipeID(4L);
        assertThat(stages).isNotNull().hasSize(2);
        assertThat(stages.stream().map(CookingStageShowDTO::getStageNumber))
                .containsExactly((short)1L, (short)2L);
        assertThat(stages.stream().map(CookingStageShowDTO::getId)).containsExactly(1L, 3L);
    }
}