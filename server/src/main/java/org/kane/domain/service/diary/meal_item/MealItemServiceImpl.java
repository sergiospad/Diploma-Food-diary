package org.kane.domain.service.diary.meal_item;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.Meal;
import org.kane.database.entity.diary.MealItem;
import org.kane.database.enum_types.NutritionType;
import org.kane.database.repository.diary.meal_item.MealItemRepository;
import org.kane.database.repository.product.ProductRepository;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;
import org.kane.exceptions.not_found.MealItemNotFoundException;
import org.kane.exceptions.not_found.NoSuchProductException;
import org.kane.exceptions.not_found.RecipeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MealItemServiceImpl implements MealItemService {

    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final MealItemRepository mealItemRepository;

    @Transactional
    @Override
    public void unpackMealItem(MealItemCreateDTO mealItemCreateDTO, Meal meal){
        var mealItem = new MealItem();
        mealItemSetNutritionType(mealItemCreateDTO.getNutritionID(),
                mealItemCreateDTO.getNutritionType(), mealItem);
        mealItem.setProductWeight(mealItemCreateDTO.getWeight());
        meal.addMealItem(mealItem);
        mealItemRepository.save(mealItem);
    }

    @Override
    @Transactional
    public void updateMealItem(MealItemEditDTO mealItemEditDTO){
        var mealItem = mealItemRepository.findById(mealItemEditDTO.getId())
                .orElseThrow(()->new MealItemNotFoundException("Meal Item Not Found"));
        mealItemSetNutritionType(mealItemEditDTO.getNutritionID(),
                mealItemEditDTO.getNutritionType(), mealItem);
        mealItem.setProductWeight(mealItemEditDTO.getWeight());
        mealItemRepository.save(mealItem);
    }

    private void mealItemSetNutritionType(Long nutritionID,  NutritionType nutritionType, MealItem mealItem){
        if(nutritionID == null) return;
        if(nutritionType == NutritionType.PRODUCT){
            var product = productRepository.findById(nutritionID)
                    .orElseThrow(()->new NoSuchProductException("product not found"));
            mealItem.setNutritionalInfo(product);
        }else if(nutritionType==NutritionType.RECIPE){
            var recipe = recipeRepository.findById(nutritionID)
                    .orElseThrow(()->new RecipeNotFoundException("recipe not found"));
            mealItem.setNutritionalInfo(recipe);
        }
    }

    @Transactional
    @Override
    public void deleteMealItem(Long id){
        mealItemRepository.deleteById(id);
    }
}
