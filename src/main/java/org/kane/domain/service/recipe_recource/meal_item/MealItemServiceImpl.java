package org.kane.domain.service.recipe_recource.meal_item;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.MealItem;
import org.kane.database.enum_types.NutritionType;
import org.kane.database.repository.diary.meal_item.MealItemRepository;
import org.kane.database.repository.product.ProductRepository;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;
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


    @Override
    public MealItem unpackMealItem(MealItemCreateDTO mealItemCreateDTO){
        var mealItem = mealItemSetNutritionType(mealItemCreateDTO.getNutritionID(),
                mealItemCreateDTO.getNutritionType());
        mealItem.setProductWeight(mealItemCreateDTO.getWeight());
        return mealItem;
    }

    @Override
    @Transactional
    public void updateMealItem(MealItemEditDTO mealItemEditDTO){
        var mealItem = mealItemSetNutritionType(mealItemEditDTO.getNutritionID(),
                mealItemEditDTO.getNutritionType());
        mealItem.setId(mealItemEditDTO.getId());
        mealItem.setProductWeight(mealItemEditDTO.getWeight());
        mealItemRepository.save(mealItem);
    }

    private MealItem mealItemSetNutritionType(Long nutritionID,  NutritionType nutritionType){
        var mealItem = new MealItem();
        if(nutritionType == NutritionType.PRODUCT){
            var product = productRepository.findById(nutritionID)
                    .orElseThrow(()->new NoSuchProductException("product not found"));
            mealItem.setNutritionalInfo(product);
        }else if(nutritionType==NutritionType.RECIPE){
            var recipe = recipeRepository.findById(nutritionID)
                    .orElseThrow(()->new RecipeNotFoundException("recipe not found"));
            mealItem.setNutritionalInfo(recipe);
        }
        return mealItem;
    }
}
