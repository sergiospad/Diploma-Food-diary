import {Component, computed, input} from '@angular/core';
import MealItemShowDTO from '../../../../../../DTO/entity_dto/diary/meal_item/meal-item-show.dto';
import MealItemCreateView from '../../../../../../DTO/entity_dto/diary/meal_item/meal-item-create.view';
import BaseNutrition from '../../../../../../DTO/entity_dto/nutritional_info/base-nurtition';

@Component({
  selector: 'app-show-meal-items',
  imports: [],
  templateUrl: './show-meal-items.html',
  styleUrl: './show-meal-items.css',
})
export class ShowMealItems {
  mealItems = input.required<MealItemShowDTO[]|MealItemCreateView[]>();
  total = input.required<BaseNutrition>();

  names = computed(()=>{
    const items = this.mealItems();
      return items.map(item =>
        'nutritionName' in item
          ? item.nutritionName
          : item.name)
  });
  weights = computed(()=>{
    const items = this.mealItems();
    return items.map(item => 'weight' in item
      ? item.weight
      :item.productWeight)
  })
}
