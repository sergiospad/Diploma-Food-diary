package org.kane.database.repository.diary.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.database.entity.User;
import org.kane.database.entity.diary.DiaryRecord;
import org.kane.database.entity.diary.MealItem;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.MealType;
import org.kane.database.enum_types.NutritionType;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/diary/data-meal-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class MealRepositoryTest extends IntegrationTestBase {


    private User savedUser;
    @Autowired
    private SavedEntities savedEntities;
    private DiaryRecord savedDiaryRecord;
    private Map<MealProjection, List<MealItemShowDTO>> savedDiaryRecordMap;
    @Autowired
    private MealRepository mealRepository;

    @BeforeEach
    void setUp() {
        savedUser = savedEntities.getUser();
        savedDiaryRecord = savedEntities.getDiaryRecord();
        savedDiaryRecordMap = new HashMap<>();
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
                new Carbs(24.0),
                NutritionType.PRODUCT.name()
        );
        list.add(mealItem);
        mealItem = new MealItemShowDTO(
                2L,
                "Яблоко",
                new ProductWeight(50.0),
                new Calories(26.0),
                new Protein(0.15),
                new Fat(0.1),
                new Carbs(7.0),
                NutritionType.PRODUCT.name()
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
                new Carbs(10.0),
                NutritionType.RECIPE.name()
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
                new Carbs(15.0),
                NutritionType.RECIPE.name()
        );
        list.add(mealItem);
        savedDiaryRecordMap.put(mealProj, list);
    }
    @Test
    void getShowDTOMap() {
        var map = mealRepository.getShowDTOMap(savedDiaryRecord.getRecordDate(), savedUser.getId());
        assertThat(map).isNotNull();
        System.out.println(map);
        var keySet = savedDiaryRecordMap.keySet().stream().toList();
        for (var key : keySet) {
            assertTrue(map.containsKey(key));
            var list = savedDiaryRecordMap.get(key);
            assertThat(map.get(key)).isNotNull().isEqualTo(list);
        }
    }

    @Test
    void showMealItems() {
        var mealProj = MealProjection.builder()
                .id(1L)
                .type(MealType.BREAKFAST)
                .time(LocalTime.of(8, 0, 0))
                .build();
        var mealItems = mealRepository.showMealItems(mealProj.getId());
        assertThat(mealItems)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(savedDiaryRecordMap.get(mealProj));
    }
}