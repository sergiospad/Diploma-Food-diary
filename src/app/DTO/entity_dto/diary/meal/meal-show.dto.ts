import {MealType} from '../../../types';
import MealItemShowDTO from '../meal_item/meal-item-show.dto';

/** `LocalTime` с бэка — строка вида `"14:30:00"` (jackson-datatype-jsr310). */
export default interface MealShowDTO {
  id: number;
  time: string;
  mealType: MealType;
  showDTOList: MealItemShowDTO[];
}
