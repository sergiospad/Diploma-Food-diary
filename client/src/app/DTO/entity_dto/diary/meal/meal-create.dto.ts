import { type CalendarDate, MealType } from '../../../types';
import MealItemCreateDTO from '../meal_item/meal-item-create.dto';

export default interface MealCreateDTO {
  dailyRecordDate: CalendarDate;
  mealType: MealType;
  time: string;
  list: MealItemCreateDTO[];
}
