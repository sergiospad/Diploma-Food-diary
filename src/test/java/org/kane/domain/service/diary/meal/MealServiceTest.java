package org.kane.domain.service.diary.meal;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.diary.Meal;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.MealType;
import org.kane.database.enum_types.NutritionType;
import org.kane.domain.DTO.entityDTO.diary.meal.MealCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealEditDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
class MealServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private MealService mealService;
    @Autowired
    private SavedEntities savedEntities;

    @Test
    void createMeal() {
        Principal principal = () -> "jane_smith";
        var micdto = MealItemCreateDTO.builder()
                .nutritionID(4L)
                .nutritionType(NutritionType.RECIPE)
                .weight(new ProductWeight(200.0))
                .build();
        MealCreateDTO mealCreateDTO = MealCreateDTO.builder()
                .dailyRecordDate(LocalDate.of(2024, 1, 15))
                .mealType(MealType.DINNER)
                .time(LocalTime.now())
                .list(List.of(micdto))
                .build();
        var meal = mealService.createMeal(principal, mealCreateDTO);
        assertThat(meal.getId()).isEqualTo(8L);
        assertThat(meal.getMealType()).isEqualTo(mealCreateDTO.getMealType());
        assertThat(meal.getTime()).isEqualTo(mealCreateDTO.getTime());
        assertThat(meal.getShowDTOList()).hasSize(1);
    }

    @Test
    void getMealShowDTO() {
        Map<MealProjection, List<MealItemShowDTO>> savedDiaryRecordMap = new HashMap<>();
        var mealProj = MealProjection.builder()
                .id(1L)
                .type(MealType.BREAKFAST)
                .time(LocalTime.of(8, 0, 0))
                .build();
        List<MealItemShowDTO> list = new ArrayList<>();
        var mealItem = new MealItemShowDTO(
                1L,
                "Овсяная каша",
                new ProductWeight(200.0),
                new Calories(136.0),
                new Protein(5.0),
                new Fat(3.0),
                new Carbs(24.0)
        );
        list.add(mealItem);
        mealItem = new MealItemShowDTO(
                2L,
                "Яблоко",
                new ProductWeight(50.0),
                new Calories(26.0),
                new Protein(0.15),
                new Fat(0.1),
                new Carbs(7.0)
        );
        list.add(mealItem);
        savedDiaryRecordMap.put(mealProj, list);
        mealProj = MealProjection.builder()
                .id(2L)
                .type(MealType.LUNCH)
                .time(LocalTime.of(13, 0, 0))
                .build();
        list =  new ArrayList<>();
        mealItem = new MealItemShowDTO(
                3L,
                "Куриный суп",
                new ProductWeight(250.0),
                new Calories(112.5),
                new Protein(11.25),
                new Fat(3.75),
                new Carbs(10.0)
        );
        list.add(mealItem);
        savedDiaryRecordMap.put(mealProj, list);
        mealProj = MealProjection.builder()
                .id(3L)
                .type(MealType.DINNER)
                .time(LocalTime.of(19, 0, 0))
                .build();
        list =  new ArrayList<>();
        mealItem = new MealItemShowDTO(
                4L,
                "Салат Цезарь",
                new ProductWeight(150.0),
                new Calories(270.0),
                new Protein(12.0),
                new Fat(18.0),
                new Carbs(15.0)
        );
        list.add(mealItem);
        savedDiaryRecordMap.put(mealProj, list);
        for (var entrySet: savedDiaryRecordMap.entrySet()) {
            var msdto = mealService.getMealShowDTO(entrySet);
            assertThat(msdto.getId()).isEqualTo(entrySet.getKey().getId());
            assertThat(msdto.getMealType()).isEqualTo(entrySet.getKey().getType());
            assertThat(msdto.getTime()).isEqualTo(entrySet.getKey().getTime());
            assertThat(msdto.getShowDTOList()).hasSize(entrySet.getValue().size());
        }
    }

    @Test
    void getTotal() {
        var msdto1 = new MealShowDTO(
                1L,
                MealType.DINNER,
                new Calories(136.0),
                new Protein(5.0),
                new Fat(3.0),
                new Carbs(24.0),
                LocalTime.now(),
                List.of()
        );
        var msdto2 = new MealShowDTO(
                2L,
                MealType.BREAKFAST,
                new Calories(26.0),
                new Protein(0.15),
                new Fat(0.1),
                new Carbs(7.0),
                LocalTime.now(),
                List.of()
        );
        var msdto3 = new MealShowDTO(
                3L,
                MealType.AFTERNOON_SNACK,
                new Calories(112.5),
                new Protein(11.25),
                new Fat(3.75),
                new Carbs(10.0),
                LocalTime.now(),
                List.of()
        );
        var total = mealService.getTotal(List.of(msdto1, msdto2, msdto3));
        assertThat(total).isNotNull();
        assertThat(total.getCalories().value).isEqualTo(Stream.of(msdto1, msdto2, msdto3)
                .map(MealShowDTO::getCalories)
                .map(Calories::toValue)
                .reduce(Double::sum)
                .get());
        assertThat(total.getProteins().value).isEqualTo(Stream.of(msdto1, msdto2, msdto3)
                .map(MealShowDTO::getProteins)
                .map(Protein::toValue)
                .reduce(Double::sum)
                .get());
        assertThat(total.getFat().value).isEqualTo(Stream.of(msdto1, msdto2, msdto3)
                .map(MealShowDTO::getFat)
                .map(Fat::toValue)
                .reduce(Double::sum)
                .get());
        assertThat(total.getCarbs().value).isEqualTo(Stream.of(msdto1, msdto2, msdto3)
                .map(MealShowDTO::getCarbs)
                .map(Carbs::toValue)
                .reduce(Double::sum)
                .get());
    }

    @Test
    void editMeal() {
        var mealItemEditDTO = MealItemEditDTO.builder()
                .id(5L)
                .nutritionID(9L)
                .nutritionType(NutritionType.RECIPE)
                .weight(new ProductWeight(500.0))
                .build();
        MealEditDTO mealEditDTO = MealEditDTO.builder()
                .mealID(4L)
                .mealType(MealType.SUPPER)
                .time(LocalTime.of(12, 10, 10))
                .mealItemEditDTOList(List.of(mealItemEditDTO))
                .build();
        var meal = mealService.editMeal(mealEditDTO);
        assertThat(meal.getId()).isEqualTo(mealEditDTO.getMealID());
        assertThat(meal.getMealType()).isEqualTo(mealEditDTO.getMealType());
        assertThat(meal.getTime()).isEqualTo(mealEditDTO.getTime());
        assertThat(meal.getShowDTOList()).hasSize(1);
        var msdto = meal.getShowDTOList().getFirst();

        assertThat(msdto.getId()).isEqualTo(mealItemEditDTO.getId());
        assertThat(msdto.getNutritionType()).isEqualTo(mealItemEditDTO.getNutritionType());
        assertThat(msdto.getProductWeight()).isEqualTo(mealItemEditDTO.getWeight());

    }
}