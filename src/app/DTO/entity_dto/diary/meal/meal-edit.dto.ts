import {MealType} from '../../../types';
import MealItemEditDTO from '../meal_item/meal-item-edit.dto';

export default interface MealEditDTO {
  mealID: number;
  mealType: MealType;
  time: string;
  mealItemEditDTOList: MealItemEditDTO[];
}
