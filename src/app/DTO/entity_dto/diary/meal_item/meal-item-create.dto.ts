import {NutritionType} from '../../../types';

export default interface MealItemCreateDTO {
  nutritionID: number;
  nutritionType: NutritionType;
  weight: number;
}
