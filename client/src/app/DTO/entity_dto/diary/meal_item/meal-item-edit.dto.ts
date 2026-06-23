import {NutritionType} from '../../../types';

export default interface MealItemEditDTO {
  id: number;
  nutritionID: number;
  nutritionType: NutritionType;
  weight: number;
}
