import BaseNutrition from '../../nutritional_info/base-nurtition';
import { MealType } from '../../../types';
import MealItemShowDTO from '../meal_item/meal-item-show.dto';

export default interface MealShowDTO extends BaseNutrition {
  id: number;
  time: string;
  mealType: MealType;
  showDTOList: MealItemShowDTO[];
}
