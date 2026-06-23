package org.kane.domain.service.energy_value;

import org.junit.jupiter.api.Test;
import org.kane.database.enum_types.CaloricityType;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.request.EnergyValueRequest;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/data-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class EnergyValueServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private EnergyValueService energyValueService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void calculateEnergyValue1() {
        var recipe = recipeRepository.findById(4L).orElseThrow();
        var energyValueRequest = EnergyValueRequest.builder()
                .recipeID(recipe.getId())
                .caloricityType(CaloricityType.PER_HUNDRED)
                .build();
        var calculateEnergyValue = energyValueService.calculateEnergyValue(energyValueRequest);
        assertThat(calculateEnergyValue).isNotNull();
        assertThat(calculateEnergyValue.getCaloricityType()).isEqualTo(CaloricityType.PER_HUNDRED);
        assertThat(Math.abs(calculateEnergyValue.getCalories().value - recipe.getCalories().value) ).as("Calories").isLessThan(0.01);
        assertThat(Math.abs(calculateEnergyValue.getFat().value - recipe.getFat().value)).as("Fat").isLessThan(0.1);
        assertThat(Math.abs(calculateEnergyValue.getCarbs().value - recipe.getCarbs().value)).as("Carbs").isLessThan(0.1);
        assertThat(Math.abs(calculateEnergyValue.getProtein().value - recipe.getProtein().value)).as("Protein").isLessThan(0.1);
    }

    @Test
    void calculateEnergyValue2() {
        var recipe = recipeRepository.findById(4L).orElseThrow();
        var energyValueRequest = EnergyValueRequest.builder()
                .recipeID(recipe.getId())
                .caloricityType(CaloricityType.ALL)
                .build();
        var calculateEnergyValue = energyValueService.calculateEnergyValue(energyValueRequest);
        assertThat(calculateEnergyValue).isNotNull();
        assertThat(calculateEnergyValue.getCaloricityType()).isEqualTo(CaloricityType.ALL);
        var coeff = calculateEnergyValue.getProductWeight().getWeightCoefficient();
        assertThat(Math.abs(calculateEnergyValue.getCalories().value - recipe.getCalories().value*coeff) ).as("Calories").isLessThan(0.04);
        assertThat(Math.abs(calculateEnergyValue.getFat().value - recipe.getFat().value*coeff)).as("Fat").isLessThan(0.4);
        assertThat(Math.abs(calculateEnergyValue.getCarbs().value - recipe.getCarbs().value*coeff)).as("Carbs").isLessThan(0.4);
        assertThat(Math.abs(calculateEnergyValue.getProtein().value - recipe.getProtein().value*coeff)).as("Protein").isLessThan(0.4);
    }

    @Test
    void getBMR() {
        var bmrInfo = userRepository.getBMRInfo(1L);
        var bmr = energyValueService.getBMR(1L);

        double correction = bmrInfo.getGender().name().equals("M") ? 5.0 : -161.0;
        double expected = 10 * bmrInfo.getWeight().value()
                + 6.25 * bmrInfo.getHeight()
                - 5 * bmrInfo.getAge()
                + correction;

        assertThat(bmr).isNotNull();
        assertThat(Math.abs(bmr.value - expected)).isLessThan(0.0001);
    }
}