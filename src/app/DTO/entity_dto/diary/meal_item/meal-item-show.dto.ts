import BaseNutrition from '../../nutritional_info/base-nurtition';
import {NutritionType} from '../../../types';

export default interface MealItemShowDTO extends BaseNutrition {
  id: number;
  name: string;
  productWeight: number;
  nutritionType: NutritionType;
}
