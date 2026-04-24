import type { CalendarDate } from '../../../types';
import MealShowDTO from '../meal/meal-show.dto';
import TotalMealShowDTO from '../meal_item/total-meal-show.dto';
import CalorieConsumptionShowDTO from '../sport_activity/calorie-consumption-show.dto';

export default interface DiaryRecordShowDTO {
  date: CalendarDate;
  meals: MealShowDTO[]
  total: TotalMealShowDTO;
  calorieConsumption: CalorieConsumptionShowDTO
}
